package org.hackathorm.api.service.game;

import lombok.extern.slf4j.Slf4j;
import org.hackathorm.api.domain.game.Game;
import org.hackathorm.api.domain.game.PlayerGameSeriesResponse;
import org.hackathorm.api.repository.game.GameRepository;
import org.hackathorm.api.service.DateService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.hackathorm.api.domain.game.PlayerGameDrawSeries.draw;
import static org.hackathorm.api.domain.game.PlayerGameLostSeries.lost;
import static org.hackathorm.api.domain.game.PlayerGameWonSeries.won;

@Slf4j
@Service
public class GameService {

    private final GameRepository repository;
    private final DateService dateService;

    public GameService(GameRepository repository, DateService dateService) {
        this.repository = repository;
        this.dateService = dateService;
    }

    public Flux<Game> list() {
        return repository.findAll();
    }

    public Mono<Game> insert(Game game) {
        return repository.save(game);
    }

    public Mono<Game> update(String gameId, @Nullable String winnerId) {
        return repository.findById(gameId)
                .flatMap(game -> {
                    game.setWinnerId(winnerId);
                    game.setFinishedAt(dateService.getCurrentDate());
                    return repository.save(game);
                });
    }

    @NotNull
    public Mono<Long> getLastGameNumber() {
        return repository.get(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "number")))
                .map(Game::getNumber)
                .reduce(0L, (def, nr) -> nr);
    }

    public Flux<PlayerGameSeriesResponse> statistics() {
        // todo player collection?
        return repository.findAll()
                .filter(game -> game.getFinishedAt().isPresent())
                .flatMap(game -> Flux.fromIterable(game.getPlayers()))
                .map(image -> image.getSubmittedBy().getId())
                .flatMap(this::calculateGameSeries);
    }

    @NotNull
    private Mono<PlayerGameSeriesResponse> calculateGameSeries(String playerId) {
        AtomicLong won = new AtomicLong();
        AtomicLong lost = new AtomicLong();
        AtomicLong draw = new AtomicLong();
        return repository.findAllByPlayerId(playerId)
                .doOnNext(game -> game.getWinner()
                        .filter(winner -> winner.equals(playerId))
                        .ifPresentOrElse(winner -> won.incrementAndGet(),
                                () -> {
                                    if (game.getWinner().isPresent())
                                        lost.incrementAndGet();
                                    else
                                        draw.incrementAndGet();
                                }))
                // does nothing
                .reduce((game1, game2) -> game1)
                .map(game -> {
                    PlayerGameSeriesResponse series = new PlayerGameSeriesResponse();
                    series.setName(getPlayersName(playerId, game));
                    series.setSeries(List.of(won(won.get()), lost(lost.get()), draw(draw.get())));
                    return series;
                });
    }

    @NotNull
    String getPlayersName(String playerId, Game game) {
        return game.getPlayers().stream()
                .filter(player -> player.getSubmittedBy().getId().equals(playerId)).findFirst()
                .map(player -> player.getSubmittedBy().getName())
                .orElseThrow();
    }
}
