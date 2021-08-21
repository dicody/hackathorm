package org.hackathorm.api.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackathorm.api.domain.game.Game;
import org.hackathorm.api.domain.game.GameResultsDto;
import org.hackathorm.api.service.game.GameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.hackathorm.api.rest.ApplicationRouter.GAMES_PATH;
import static org.hackathorm.api.rest.ApplicationRouter.ROOT_PATH;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GameResultsController {

    private final GameService gameService;
    private final ObjectMapper objectMapper;

    @Topic(name = "gameResults", pubsubName = "pubsub")
    @PostMapping(path = ROOT_PATH + GAMES_PATH)
    public Mono<Game> handleGameResults(@RequestBody CloudEvent cloudEvent) {
        GameResultsDto results = objectMapper.convertValue(cloudEvent.getData(), GameResultsDto.class);
        log.info("got game results: {}", results);

        return gameService.update(results.getGameId(), results.getWinner());
    }
}
