package org.hackathorm.api.service.game;

import org.hackathorm.api.domain.game.Game;
import org.hackathorm.api.domain.image.SolutionImage;
import org.hackathorm.api.domain.player.Player;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;
    @Mock
    private Game game;

    @Test
    void shouldReturnPlayersName() {
        Player player1 = player("player1", "PlayerOne");
        Player player2 = player("player2", "PlayerTwo");

        SolutionImage image1 = Mockito.mock(SolutionImage.class);
        when(image1.getSubmittedBy()).thenReturn(player1);
        SolutionImage image2 = Mockito.mock(SolutionImage.class);
        when(image2.getSubmittedBy()).thenReturn(player2);

        when(game.getPlayers()).thenReturn(List.of(image2, image1));
        Assertions.assertEquals(
                "PlayerOne",
                gameService.getPlayersName("player1", game));
    }

    @Test
    void shouldThrowExceptionWhenNoPlayerNameFound() {
        when(game.getPlayers()).thenReturn(Collections.emptyList());
        assertThrows(
                NoSuchElementException.class,
                () -> gameService.getPlayersName("missingPlayerId", game)
        );
    }

    @NotNull
    private Player player(String id, String name) {
        Player player = new Player();
        player.setId(id);
        player.setName(name);
        return player;
    }
}