package com.hackathorm.examples.controller;

import com.hackathorm.examples.domain.PlayerDto;
import com.hackathorm.examples.service.GameService;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PlayersController {

    private final GameService gameService;

    @Topic(name = "players", pubsubName = "pubsub")
    @PostMapping(path = "/players")
    public Mono<Void> handleMessage(@RequestBody(required = false) CloudEvent cloudEvent) {
        return Mono.fromRunnable(() -> {
            // todo define model for player request!
            PlayerDto player = new PlayerDto();
//            player.setName(request.getName());
//            player.setName(((PlayerRequest) cloudEvent.getData()).getName());
            player.setServiceAppId(cloudEvent.getSource());
            gameService.addPlayerToQueue(player);
        });
    }
}
