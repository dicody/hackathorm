package org.hackathorm.api.service.feedback;

import org.hackathorm.api.domain.feedback.Feedback;
import org.hackathorm.api.repository.feedback.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository repository;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    void list() {
        when(repository.findAll()).thenReturn(Flux.empty());
        assertEquals(Flux.empty(), feedbackService.list());
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void insert() {
        Feedback feedback = new Feedback();
        when(repository.save(feedback)).thenReturn(Mono.just(feedback));
        assertEquals(feedback, feedbackService.insert(feedback).block());
        verify(repository).save(feedback);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void get() {
        String id = "feedbackId";
        Feedback feedback = new Feedback();
        when(repository.findById(id)).thenReturn(Mono.just(feedback));
        assertEquals(feedback, feedbackService.get(id).block());
        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }
}