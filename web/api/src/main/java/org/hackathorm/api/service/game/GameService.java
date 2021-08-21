package org.hackathorm.api.service.game;

import lombok.extern.slf4j.Slf4j;
import org.hackathorm.api.domain.game.Game;
import org.hackathorm.api.repository.game.GameRepository;
import org.hackathorm.api.service.DateService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
                    game.setWinner(
                            Optional.ofNullable(winnerId)
                                    .map(winner -> game.getPlayers().stream()
                                            .filter(player -> player.getSubmittedBy().getId().equals(winnerId))
                                            .findFirst()
                                            .orElseThrow())
                                    .orElse(null)
                    );
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
}
