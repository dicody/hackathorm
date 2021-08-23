package com.hackathorm.examples.integration;

import com.hackathorm.examples.domain.GameResultsDto;
import io.dapr.client.DaprClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.hackathorm.examples.configuration.AppConfigProperties.GAME_RESULTS_TOPIC;
import static com.hackathorm.examples.configuration.AppConfigProperties.PUBSUB_NAME;

@Component
@RequiredArgsConstructor
public class AdminClient {

    private final DaprClient daprClient;

    public Mono<Void> notifyWithWinner(String gameId, String winnerId) {
        return daprClient.publishEvent(PUBSUB_NAME, GAME_RESULTS_TOPIC,
                GameResultsDto.builder().gameId(gameId).winnerId(winnerId).build());
    }

    public Mono<Void> notifyWithTie(String gameId) {
        return daprClient.publishEvent(PUBSUB_NAME, GAME_RESULTS_TOPIC,
                GameResultsDto.builder().gameId(gameId).build());
    }
}
