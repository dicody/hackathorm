package org.hackathorm.api.integration;

import io.dapr.client.domain.CloudEvent;
import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.game.GameResultsDto;
import org.hackathorm.api.domain.gamemaster.GameRequest;
import org.hackathorm.api.pubsub.GameResultsController;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Random;

@Profile("localhost")
@Component
@RequiredArgsConstructor
public class GameMasterClientMock implements GameMasterClient {

    private final GameResultsController controller;
    private final Random random = new Random();

    public Mono<Void> publishGame(GameRequest data) {
        CloudEvent event = new CloudEvent();
        GameResultsDto gameResultsDto = new GameResultsDto();
        gameResultsDto.setGameId(data.getGameId());
        gameResultsDto.setWinnerId(randomWinner(data.getP1Id(), data.getP2Id()));
        event.setData(gameResultsDto);
        return controller.handleGameResults(event)
                .then();
    }

    @Nullable
    private String randomWinner(String p1Id, String p2Id) {
        int randInt = random.nextInt(15);
        if (randInt >= 10) return null;
        else if (randInt <= 5) return p1Id;
        else return p2Id;
    }
}
