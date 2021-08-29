package org.hackathorm.api.integration;

import io.dapr.client.DaprClient;
import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.gamemaster.GameRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static org.hackathorm.api.conf.AppConfigProperties.GAME_MASTER_PLAYERS_TOPIC;
import static org.hackathorm.api.conf.AppConfigProperties.PUBSUB_NAME;

@Profile("!localhost")
@Component
@RequiredArgsConstructor
public class GameMasterClientImpl implements GameMasterClient {
    private final DaprClient daprClient;

    public Mono<Void> publishGame(GameRequest data) {
        return daprClient.publishEvent(PUBSUB_NAME, GAME_MASTER_PLAYERS_TOPIC, data);
    }

}
