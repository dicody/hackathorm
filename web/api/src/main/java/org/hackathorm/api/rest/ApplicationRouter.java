package org.hackathorm.api.rest;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.rest.feedback.FeedbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
@RequiredArgsConstructor
public class ApplicationRouter {

    private final FeedbackHandler feedbackHandler;

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions
                .route(GET("/feedback"), feedbackHandler::list)
                .and(RouterFunctions.route(POST("/feedback"), feedbackHandler::insert));
    }

}
