package org.hackathorm.api.service.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackathorm.api.domain.game.Game;
import org.hackathorm.api.domain.image.SolutionImage;
import org.hackathorm.api.service.DateService;
import org.hackathorm.api.service.gamemaster.GameMasterService;
import org.hackathorm.api.service.image.SolutionImageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledGameExecutor {

    private final SolutionImageService solutionImageService;
    private final GameService gameService;
    private final DateService dateService;
    private final GameMasterService gameMasterService;

    @Scheduled(fixedDelay = 10000) // 10s
    public void scheduleNextGame() {
        log.debug("scheduled game executor started");
        getLatestImagesPairThatNotYetPlayed()
                .zipWith(gameService.getLastGameNumber(), (tuple, gameNr) -> {
                    Game game = new Game();
                    game.setNumber(gameNr + 1);
                    game.setPlayers(List.of(tuple.getT1(), tuple.getT2()));
                    game.setStartedAt(dateService.getCurrentDate());
                    return gameService.insert(game);
                })
                .flatMap(game -> game)
                .doOnNext(game -> log.debug("new game created: {}", game))

                .flatMap(gameMasterService::publishNewGame)
                .doOnNext(game -> log.debug("player published to game master"))

                .subscribe(
                        game -> log.debug("scheduleNextGame consumer: {}", game),
                        throwable -> log.error("scheduleNextGame error", throwable),
                        () -> log.debug("scheduleNextGame onComplete"));
    }

    private Flux<Tuple2<SolutionImage, SolutionImage>> getLatestImagesPairThatNotYetPlayed() {
        return solutionImageService.list()
                .groupBy(image -> image.getSubmittedBy().getId())
                .flatMap(grouped -> grouped.reduce((image1, image2) -> image1.getSubmittedAt().compareTo(image2.getSubmittedAt()) > 0 ? image1 : image2))
                .collect(Collectors.toSet())
                .zipWith(gameService.list().collectList(),
                        (images, games) -> images.stream()
                                .flatMap(image1 -> images.stream()
                                        .filter(image2 -> !image2.equals(image1)
                                                && (games.isEmpty() || games.stream().noneMatch(game -> game.getPlayers().containsAll(List.of(image1, image2)))))
                                        .map(image2 -> Set.of(image1, image2)))
                                .collect(Collectors.toSet()))
                .flatMapIterable(images -> images)
                .map(iterable -> {
                    Iterator<SolutionImage> iterator = iterable.iterator();
                    return Tuples.of(iterator.next(), iterator.next());
                });
    }

}
