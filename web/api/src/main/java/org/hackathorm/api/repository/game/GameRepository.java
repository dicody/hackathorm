package org.hackathorm.api.repository.game;

import org.hackathorm.api.domain.game.Game;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends ReactiveCrudRepository<Game, String> {
}
