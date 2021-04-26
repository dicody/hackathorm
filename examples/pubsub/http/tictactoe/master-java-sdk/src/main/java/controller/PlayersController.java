package controller;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PlayersController {

    @Topic(name = "testingtopic", pubsubName = "messagebus")
    @PostMapping(path = "/testingtopic")
    public Mono<Void> handleMessage(@RequestBody(required = false) CloudEvent cloudEvent) {
        return Mono.fromRunnable(() -> {

        });
    }
}
