package org.hackathorm.api.service.image;

import lombok.RequiredArgsConstructor;
import org.hackathorm.api.domain.image.Image;
import org.hackathorm.api.domain.player.Player;
import org.hackathorm.api.repository.image.ImageRepository;
import org.hackathorm.api.service.DateService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository repository;
    private final DateService dateService;

    public Flux<Image> list() {
        return repository.findAll();
    }

    public Mono<Image> insert(Image image) {
        image.setSubmittedAt(dateService.getCurrentDate());
        // todo get principal from request (OiDC + github/google?)
        Player submittedBy = new Player();
        submittedBy.setId(image.getName());
        submittedBy.setName(image.getName());
        image.setSubmittedBy(submittedBy);
        return repository.save(image);
    }

    public Mono<Image> get(String id) {
        return repository.findById(id);
    }
}
