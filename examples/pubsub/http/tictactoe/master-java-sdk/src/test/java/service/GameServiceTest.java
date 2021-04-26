package service;

import domain.Player;
import integration.PlayerClient;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @InjectMocks
    private GameService gameService;
    @Mock
    private PlayerClient playerClient;
    @Mock
    private Player player;

    @SuppressWarnings("unused")
    private static Stream<Arguments> providerForWinnerCheck() {
        return Stream.of(
                // row
                of(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'X', 'X', 'X'},
                        new Character[]{'O', 'X', 'O'}}, true),
                of(new Character[][]{
                        new Character[]{'O', null, 'O'},
                        new Character[]{'X', 'X', 'X'},
                        new Character[]{null, null, null}}, true),

                // column
                of(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'O', 'X', 'X'},
                        new Character[]{'O', 'X', 'O'}}, true),
                of(new Character[][]{
                        new Character[]{'O', null, 'X'},
                        new Character[]{null, null, 'X'},
                        new Character[]{'O', null, 'X'}}, true),

                // diagonal
                of(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'O', 'X', 'X'},
                        new Character[]{'X', 'X', 'O'}}, true),
                of(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'X', 'X', 'O'}}, true),

                // no winners
                of(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'X', 'O', 'O'},
                        new Character[]{'O', 'X', 'X'}}, false),
                of(new Character[][]{
                        new Character[]{null, null, null},
                        new Character[]{null, null, null},
                        new Character[]{null, null, null}}, false)
        );
    }

    @Test
    public void shouldNotViolateOnPlayersMove() {
        Character[][] board = {
                new Character[]{'X', 'O', 'X'},
                new Character[]{'X', 'O', 'X'},
                new Character[]{'O', null, null}};
        Character[][] playerMove = {
                new Character[]{'X', 'O', 'X'},
                new Character[]{'X', 'O', 'X'},
                new Character[]{'O', null, 'O'}};
        when(playerClient.getMove(board, player)).thenReturn(playerMove);
        Character[][] boardWithPlayersMove = gameService.move(board, player);
        assertThat(boardWithPlayersMove, equalTo(playerMove));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldViolateOnCorruptedBoard() {
        Character[][] board = {
                new Character[]{'X', 'O', 'X'},
                new Character[]{'X', 'O', 'X'},
                new Character[]{'O', 'X', null}};
        when(playerClient.getMove(board, player))
                .thenReturn(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'X', 'O', 'X'},
                        new Character[]{'O', 'X', 'O'}});
        gameService.move(board, player);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldViolateOnCorruptedBoardCellCountDiffers() {
        Character[][] board = {
                new Character[]{'X', 'O', 'X'},
                new Character[]{'X', 'O', 'X'},
                new Character[]{'O', 'X', null}};
        when(playerClient.getMove(board, player))
                .thenReturn(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'X', 'O', 'X'},
                        new Character[]{'O', 'X', 'O', 'X'}});
        gameService.move(board, player);
    }

    @ParameterizedTest
    @MethodSource("providerForWinnerCheck")
    public void checkWinners(Character[][] board, boolean winner) {
        assertThat(gameService.hasWinner(board), equalTo(winner));
    }
}