package com.hackathorm.examples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathorm.examples.configuration.AppConfigProperties;
import com.hackathorm.examples.domain.PlayerDto;
import com.hackathorm.examples.domain.PlayerRequest;
import com.hackathorm.examples.service.GameService;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlayersController {

    private final GameService gameService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Topic(name = "players", pubsubName = AppConfigProperties.PUBSUB_NAME)
    @PostMapping(path = "/players")
    public Mono<Void> handleMessage(@RequestBody CloudEvent cloudEvent) {
        return Mono.fromRunnable(() -> {
            PlayerRequest request = objectMapper.convertValue(cloudEvent.getData(), PlayerRequest.class);
            log.info("got player request: {}", request);

            // todo define model for player request!
            PlayerDto player = new PlayerDto();
            player.setGameId(request.getGameId());
            player.setName(request.getName());
            player.setServiceAppId(request.getServiceId());
            gameService.addPlayerToQueue(player);
        });
    }
}
