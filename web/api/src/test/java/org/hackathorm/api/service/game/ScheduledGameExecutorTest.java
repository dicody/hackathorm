package org.hackathorm.api.service.game;

import org.hackathorm.api.domain.game.Game;
import org.hackathorm.api.domain.image.SolutionImage;
import org.hackathorm.api.domain.player.Player;
import org.hackathorm.api.service.DateService;
import org.hackathorm.api.service.gamemaster.GameMasterService;
import org.hackathorm.api.service.image.SolutionImageService;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduledGameExecutorTest {

    @InjectMocks
    private ScheduledGameExecutor executor;

    @Mock
    private SolutionImageService solutionImageService;
    @Mock
    private GameService gameService;
    @Mock
    private DateService dateService;
    @Mock
    private GameMasterService gameMasterService;

    @Test
    void getLatestImagePairsTakesLatestImage() {
        SolutionImage image1 = prepareImage("p1", "first");
        SolutionImage image2 = prepareImage("p2", "p2");
        SolutionImage image3 = prepareImage("p1", "second");
        when(solutionImageService.list()).thenReturn(Flux.fromIterable(Arrays.asList(image1, image2, image3)));
        when(gameService.list()).thenReturn(Flux.empty());

        StepVerifier
                .create(executor.getLatestImagesPairThatNotYetPlayed())
                .expectNextMatches(tuple -> Arrays.asList(tuple.getT1().getName(), tuple.getT2().getName()).contains("second"))
                .expectComplete()
                .verify();
    }

    @Test
    void getLatestImagePairs() {
        SolutionImage image1 = prepareImage("p1");
        SolutionImage image2 = prepareImage("p2");
        when(solutionImageService.list()).thenReturn(Flux.fromIterable(Arrays.asList(image1, image2)));
        when(gameService.list()).thenReturn(Flux.empty());

        StepVerifier
                .create(executor.getLatestImagesPairThatNotYetPlayed())
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void getLatestImagePairsWithSamePair() {
        SolutionImage image1 = prepareImage("p1");
        SolutionImage image2 = prepareImage("p1");
        when(solutionImageService.list()).thenReturn(Flux.just(image1, image2));
        when(gameService.list()).thenReturn(Flux.empty());

        StepVerifier
                .create(executor.getLatestImagesPairThatNotYetPlayed())
                .expectComplete()
                .verify();
    }

    @Test
    void getLatestImagePairsAlreadyPlayed() {
        SolutionImage image1 = prepareImage("p1");
        SolutionImage image2 = prepareImage("p2");
        when(solutionImageService.list()).thenReturn(Flux.just(image1, image2));

        Game game = new Game();
        game.setPlayers(Arrays.asList(image1, image2));
        when(gameService.list()).thenReturn(Flux.just(game));

        StepVerifier
                .create(executor.getLatestImagesPairThatNotYetPlayed())
                .expectComplete()
                .verify();
    }

    private SolutionImage prepareImage(String playerId) {
        return prepareImage(playerId, null);
    }

    private SolutionImage prepareImage(String playerId, @Nullable String name) {
        SolutionImage image = new SolutionImage();
        Player player = new Player();
        player.setId(playerId);
        image.setName(name);
        image.setSubmittedBy(player);
        image.setSubmittedAt(new Date());
        return image;
    }
}
