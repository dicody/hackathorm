package org.hackathorm.api.rest.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackathorm.api.domain.game.GameResponse;
import org.hackathorm.api.domain.game.PlayerGameSeriesResponse;
import org.hackathorm.api.service.game.GameService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hackathorm.api.domain.game.PlayerGameDrawSeries.draw;
import static org.hackathorm.api.domain.game.PlayerGameLostSeries.lost;
import static org.hackathorm.api.domain.game.PlayerGameWonSeries.won;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameHandler {

    private final GameService gameService;

    @NonNull
    public Mono<ServerResponse> list(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(gameService.list().map(GameResponse::from), GameResponse.class);
    }

    @NonNull
    public Mono<ServerResponse> get(ServerRequest request, String pathVariable) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    public Mono<ServerResponse> statistics(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(gameService.statistics(), PlayerGameSeriesResponse.class);
    }
}
