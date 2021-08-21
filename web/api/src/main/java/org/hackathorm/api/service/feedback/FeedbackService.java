package org.hackathorm.api.service.feedback;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.feedback.Feedback;
import org.hackathorm.api.repository.feedback.FeedbackRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository repository;

    public Flux<Feedback> list() {
        return repository.findAll();
    }

    public Mono<Feedback> insert(Feedback feedback) {
        return repository.save(feedback);
    }

    public Mono<Feedback> get(String id) {
        return repository.findById(id);
    }
}
