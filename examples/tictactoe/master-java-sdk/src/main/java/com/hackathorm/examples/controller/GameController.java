package com.hackathorm.examples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathorm.examples.configuration.AppConfigProperties;
import com.hackathorm.examples.domain.GameRequest;
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
public class GameController {

    private final GameService gameService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Topic(name = AppConfigProperties.PLAYERS_TOPIC, pubsubName = AppConfigProperties.PUBSUB_NAME)
    @PostMapping(path = "/players")
    public Mono<String> handleMessage(@RequestBody CloudEvent cloudEvent) {
        GameRequest request = objectMapper.convertValue(cloudEvent.getData(), GameRequest.class);
        log.info("got player request: {}", request);

        return gameService.runGame(request.getGameId(),
                request.getP1Id(), request.getP1ServiceId(),
                request.getP2Id(), request.getP2ServiceId());
    }
}
