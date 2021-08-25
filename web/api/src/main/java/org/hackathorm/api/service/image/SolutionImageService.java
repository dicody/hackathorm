package org.hackathorm.api.service.image;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.image.SolutionImage;
import org.hackathorm.api.domain.player.Player;
import org.hackathorm.api.repository.image.SolutionImageRepository;
import org.hackathorm.api.service.DateService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SolutionImageService {

    private final SolutionImageRepository repository;
    private final DateService dateService;

    public Flux<SolutionImage> list() {
        return repository.findAll();
    }

    public Mono<SolutionImage> insert(SolutionImage solutionImage) {
        solutionImage.setSubmittedAt(dateService.getCurrentDate());
        // todo get principal from request (OiDC + github/google?)
        Player submittedBy = new Player();
        submittedBy.setId(solutionImage.getName());
        submittedBy.setName(solutionImage.getName());
        solutionImage.setSubmittedBy(submittedBy);
        return repository.save(solutionImage);
    }

    public Mono<SolutionImage> get(String id) {
        return repository.findById(id);
    }
}
