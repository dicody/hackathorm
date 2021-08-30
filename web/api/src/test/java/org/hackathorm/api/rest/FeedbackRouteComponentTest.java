package org.hackathorm.api.rest;

import org.hackathorm.api.domain.feedback.Feedback;
import org.hackathorm.api.rest.feedback.FeedbackHandler;
import org.hackathorm.api.rest.game.GameHandler;
import org.hackathorm.api.rest.image.SolutionImageHandler;
import org.hackathorm.api.service.feedback.FeedbackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Collections.nCopies;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SuppressWarnings("unused")
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ApplicationRouter.class)
@Import(FeedbackHandler.class)
class FeedbackRouteComponentTest {

    @MockBean
    private FeedbackService feedbackService;
    @MockBean
    private SolutionImageHandler solutionImageHandler;
    @MockBean
    private GameHandler gameHandler;

    @Autowired
    private WebTestClient webClient;

    @Test
    void postNewFeedback() {
        when(feedbackService.insert(any())).thenReturn(Mono.just(new Feedback()));
        webClient
                .post()
                .uri("/api/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"rate\":\"LIKE\", \"comment\": \"No comment\"}")
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    @Test
    void postNewFeedbackShouldFailOnUnknownRate() {
        when(feedbackService.insert(any())).thenReturn(Mono.just(new Feedback()));
        webClient
                .post()
                .uri("/api/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"rate\":\"UNKNOWN_RATE\", \"comment\":\"No comment\"}")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void returnAllFeedbacks() {
        when(feedbackService.list()).thenReturn(Flux.fromIterable(nCopies(5, new Feedback())));
        webClient
                .get()
                .uri("/api/feedbacks")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Feedback.class).hasSize(5);
    }
}