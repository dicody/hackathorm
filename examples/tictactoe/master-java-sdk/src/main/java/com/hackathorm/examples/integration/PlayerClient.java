package com.hackathorm.examples.integration;

import com.hackathorm.examples.domain.PlayerDto;
import com.hackathorm.examples.domain.PlayerMoveRequest;
import com.hackathorm.examples.domain.PlayerMoveResponse;
import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Component
@RequiredArgsConstructor
public class PlayerClient {

    private final String METHOD_NAME = "move";
    private final DaprClient daprClient;

    public Mono<Tuple2<Integer, Integer>> getMove(Character[][] board, PlayerDto player) {
        PlayerMoveRequest request = new PlayerMoveRequest();
        request.setPlayerSign(player.getSign());
        request.setBoard(board);

        return daprClient.invokeMethod(player.getServiceAppId(), METHOD_NAME, request,
                HttpExtension.POST, null, PlayerMoveResponse.class)
                .map(res -> Tuples.of(res.getX(), res.getY()));
    }
}
