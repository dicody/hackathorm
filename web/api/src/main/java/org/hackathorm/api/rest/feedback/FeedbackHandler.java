package org.hackathorm.api.rest.feedback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackathorm.api.domain.feedback.Feedback;
import org.hackathorm.api.service.feedback.FeedbackService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedbackHandler {

    private final FeedbackService service;

    public Mono<ServerResponse> list(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.list(), Feedback.class);
    }

    public Mono<ServerResponse> insert(ServerRequest request) {
        log.info("new feedback request");
        return request.bodyToMono(Feedback.class)
                .doOnNext(feedback -> log.info("got feedback from request body: {}", feedback))
                .flatMap(service::insert)
                .flatMap(feedback ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(feedback));
    }
}
