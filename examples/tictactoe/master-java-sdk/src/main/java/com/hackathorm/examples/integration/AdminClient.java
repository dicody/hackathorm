package com.hackathorm.examples.integration;

import com.hackathorm.examples.configuration.AppConfigProperties;
import com.hackathorm.examples.domain.GameResultsDto;
import com.hackathorm.examples.domain.PlayerDto;
import io.dapr.client.DaprClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AdminClient {

    private final AppConfigProperties appConfig;
    private final DaprClient daprClient;

    public Mono<Void> notifyWithWinner(PlayerDto winner) {
        return daprClient.publishEvent(
                appConfig.getPubsubName(),
                appConfig.getGameResultsTopic(),
                GameResultsDto.builder().gameId(winner.getGameId()).winner(winner.getName()).build());
    }

    public Mono<Void> notifyWithTie(String gameId) {
        return daprClient.publishEvent(
                appConfig.getPubsubName(),
                appConfig.getGameResultsTopic(),
                GameResultsDto.builder().gameId(gameId).build());
    }
}
