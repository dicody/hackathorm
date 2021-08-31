package org.hackathorm.api.repository.feedback;

import org.hackathorm.api.domain.feedback.Feedback;
import org.hackathorm.api.domain.feedback.FeedbackRate;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Ignore
@SuppressWarnings("unused")
@Testcontainers
@DataMongoTest
class FeedbackRepositoryIntegrationTest {

    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.2");

    @Autowired
    private FeedbackRepository repository;

    @Test
    void feedbackSave() {
        Feedback feedback = new Feedback();
        feedback.setRate(FeedbackRate.LIKE);
        feedback.setComment("no comment");

        assertNull(feedback.getId());
        StepVerifier.create(repository.save(feedback))
                .assertNext(savedFeedback -> assertNotNull(savedFeedback.getId()))
                .expectComplete()
                .verify();
    }
}