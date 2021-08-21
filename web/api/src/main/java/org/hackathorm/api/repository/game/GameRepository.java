package org.hackathorm.api.repository.game;

import org.hackathorm.api.domain.game.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GameRepository extends ReactiveCrudRepository<Game, String> {
    @Query(value = "{}")
    Flux<Game> get(Pageable pageable);
}
