package org.hackathorm.api.rest.feedback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackathorm.api.domain.feedback.Feedback;
import org.hackathorm.api.service.feedback.FeedbackService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedbackHandler {

    private final FeedbackService service;

    @NonNull
    public Mono<ServerResponse> get(ServerRequest request, String pathVariable) {
        String id = request.pathVariable(pathVariable);
        log.info("get feedback request by id: {}", id);
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(service.get(id), Feedback.class);
    }

    @NonNull
    public Mono<ServerResponse> list(ServerRequest request) {
        log.info("list feedbacks request");
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(service.list(), Feedback.class);
    }

    @NonNull
    public Mono<ServerResponse> insert(ServerRequest request) {
        log.info("new feedback request");
        return request.bodyToMono(Feedback.class)
                .doOnNext(feedback -> log.info("got feedback: {}", feedback))
                .flatMap(service::insert)
                .flatMap(feedback -> created(request.uriBuilder().pathSegment(feedback.getId()).build()).build());
    }
}
