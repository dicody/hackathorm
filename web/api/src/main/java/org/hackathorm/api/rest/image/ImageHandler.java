package org.hackathorm.api.rest.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackathorm.api.domain.image.Image;
import org.hackathorm.api.domain.image.ImageResponse;
import org.hackathorm.api.service.image.ImageService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.hackathorm.api.conf.Formatters.ONLY_TIME_FORMAT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageHandler {

    private final ImageService service;

    @NonNull
    public Mono<ServerResponse> get(ServerRequest request, String pathVariable) {
        String id = request.pathVariable(pathVariable);
        log.info("got image request by id: {}", id);
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(service.get(id), Image.class);
    }

    @NonNull
    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        log.info("list images request");
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(service
                        .list()
                        .map(image ->
                                new ImageResponse(
                                        image.getId(), image.getName(),
                                        image.getSubmittedBy().getName(),
                                        ONLY_TIME_FORMAT.format(image.getSubmittedAt()))), ImageResponse.class);
    }

    @NonNull
    public Mono<ServerResponse> insert(ServerRequest request) {
        log.info("new image request");
        return request.bodyToMono(Image.class)
                .doOnNext(image -> log.info("got image: {}", image))
                .flatMap(service::insert)
                .flatMap(image -> created(request.uriBuilder().pathSegment(image.getId()).build()).build());
    }
}
