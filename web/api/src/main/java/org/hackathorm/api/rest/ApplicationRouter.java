package org.hackathorm.api.rest;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.rest.feedback.FeedbackHandler;
import org.hackathorm.api.rest.game.GameHandler;
import org.hackathorm.api.rest.image.ImageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class ApplicationRouter {

    public static final String ROOT_PATH = "/api";
    public static final String GAMES_PATH = "/games";

    private final FeedbackHandler feedbackHandler;
    private final ImageHandler imageHandler;
    private final GameHandler gameHandler;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return nest(path(ROOT_PATH),
                nest(
                        path("/feedbacks"),
                                route(GET(""), feedbackHandler::list)
                                .andRoute(POST(""), feedbackHandler::insert)
                                .andRoute(GET("/{id}"), request -> feedbackHandler.get(request, "id"))
                )
                .andNest(
                        path("/images"),
                                route(GET(""), imageHandler::list)
                                .andRoute(POST(""), imageHandler::insert)
                                .andRoute(GET("/{id}"), request -> imageHandler.get(request, "id"))
                )
                .andNest(
                        path(GAMES_PATH),
                                route(GET(""), gameHandler::list)
                                .andRoute(GET("/statistics"), gameHandler::statistics)
                                .andRoute(GET("/{id}"), request -> gameHandler.get(request, "id"))
                )
        );
    }
}
