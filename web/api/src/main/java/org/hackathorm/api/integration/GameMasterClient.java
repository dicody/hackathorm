package org.hackathorm.api.integration;

import org.hackathorm.api.domain.gamemaster.GameRequest;
import reactor.core.publisher.Mono;

public interface GameMasterClient {
    Mono<Void> publishGame(GameRequest data);
}
