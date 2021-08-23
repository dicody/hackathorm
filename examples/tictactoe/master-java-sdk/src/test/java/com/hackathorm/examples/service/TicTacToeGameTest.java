package com.hackathorm.examples.service;

import com.hackathorm.examples.domain.PlayerDto;
import com.hackathorm.examples.integration.PlayerClient;
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

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.just;
import static reactor.util.function.Tuples.of;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class TicTacToeGameTest {

    @InjectMocks
    private TicTacToeGame ticTacToeGame;
    @Mock
    private PlayerClient playerClient;

    @SuppressWarnings("unused")
    private static Stream<Arguments> providerForWinnerCheck() {
        return Stream.of(
                // row
                Arguments.of(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'X', 'X', 'X'},
                        new Character[]{'O', 'X', 'O'}}, true),
                Arguments.of(new Character[][]{
                        new Character[]{'O', null, 'O'},
                        new Character[]{'X', 'X', 'X'},
                        new Character[]{null, null, null}}, true),

                // column
                Arguments.of(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'O', 'X', 'X'},
                        new Character[]{'O', 'X', 'O'}}, true),
                Arguments.of(new Character[][]{
                        new Character[]{'O', null, 'X'},
                        new Character[]{null, null, 'X'},
                        new Character[]{'O', null, 'X'}}, true),

                // diagonal
                Arguments.of(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'O', 'X', 'X'},
                        new Character[]{'X', 'X', 'O'}}, true),
                Arguments.of(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'X', 'X', 'O'}}, true),

                // no winners
                Arguments.of(new Character[][]{
                        new Character[]{'O', 'O', 'X'},
                        new Character[]{'X', 'O', 'O'},
                        new Character[]{'O', 'X', 'X'}}, false),
                Arguments.of(new Character[][]{
                        new Character[]{null, null, null},
                        new Character[]{null, null, null},
                        new Character[]{null, null, null}}, false)
        );
    }

    @Test
    public void runGame_firstPlayerWins() {
        PlayerDto player1 = new PlayerDto("p1", "p1", 'X');
        PlayerDto player2 = new PlayerDto("p2", "p2", 'O');

        when(playerClient.getMove(any(), eq(player1)))
                .thenReturn(just(of(0, 0)))
                .thenReturn(just(of(1, 0)))
                .thenReturn(just(of(2, 0)));

        when(playerClient.getMove(any(), eq(player2)))
                .thenReturn(just(of(0, 2)))
                .thenReturn(just(of(1, 2)));

        Optional<PlayerDto> winner = ticTacToeGame.runGame(player1, player2);
        assertThat(winner.isPresent(), equalTo(true));
        assertThat(winner.get(), equalTo(player1));
    }

    @Test
    public void runGame_secondPlayerWins() {
        PlayerDto player1 = new PlayerDto("p1", "p1", 'X');
        PlayerDto player2 = new PlayerDto("p2", "p2", 'O');

        when(playerClient.getMove(any(), eq(player1)))
                .thenReturn(just(of(0, 0)))
                .thenReturn(just(of(1, 0)))
                .thenReturn(just(of(1, 1)));

        when(playerClient.getMove(any(), eq(player2)))
                .thenReturn(just(of(0, 2)))
                .thenReturn(just(of(1, 2)))
                .thenReturn(just(of(2, 2)));

        Optional<PlayerDto> winner = ticTacToeGame.runGame(player1, player2);
        assertThat(winner.isPresent(), equalTo(true));
        assertThat(winner.get(), equalTo(player2));
    }

    @Test
    public void runGame_draw() {
        PlayerDto player1 = new PlayerDto("p1", "p1", 'X');
        PlayerDto player2 = new PlayerDto("p2", "p2", 'O');

        when(playerClient.getMove(any(), eq(player1)))
                .thenReturn(just(of(0, 0)))
                .thenReturn(just(of(0, 2)))
                .thenReturn(just(of(1, 2)))
                .thenReturn(just(of(2, 0)))
                .thenReturn(just(of(2, 1)));

        when(playerClient.getMove(any(), eq(player2)))
                .thenReturn(just(of(0, 1)))
                .thenReturn(just(of(1, 0)))
                .thenReturn(just(of(1, 1)))
                .thenReturn(just(of(2, 2)));

        Optional<PlayerDto> winner = ticTacToeGame.runGame(player1, player2);
        assertThat(winner.isEmpty(), equalTo(true));
    }

    @Test
    public void shouldNotViolateOnPlayersMove() {
        PlayerDto player = new PlayerDto("p", "p", 'O');
        Character[][] board = {
                new Character[]{'X', 'O', 'X'},
                new Character[]{'X', 'O', 'X'},
                new Character[]{'O', null, null}};
        when(playerClient.getMove(board, player)).thenReturn(just(of(2, 2)));
        Character[][] boardWithPlayersMove = ticTacToeGame.move(board, player);
        assertThat(boardWithPlayersMove,
                equalTo(new Character[][]{
                        new Character[]{'X', 'O', 'X'},
                        new Character[]{'X', 'O', 'X'},
                        new Character[]{'O', null, 'O'}}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldViolateOnXValueBelowPossible() {
        PlayerDto player = new PlayerDto("p", "p", 'O');
        Character[][] board = {
                new Character[]{'X', 'O', 'X'},
                new Character[]{'X', 'O', 'X'},
                new Character[]{'O', 'X', null}};
        when(playerClient.getMove(board, player))
                .thenReturn(just(of(-1, 2)));
        ticTacToeGame.move(board, player);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldViolateOnXValueAbovePossible() {
        PlayerDto player = new PlayerDto("p", "p", 'O');
        Character[][] board = {
                new Character[]{'X', 'O', 'X'},
                new Character[]{'X', 'O', 'X'},
                new Character[]{'O', 'X', null}};
        when(playerClient.getMove(board, player))
                .thenReturn(just(of(3, 2)));
        ticTacToeGame.move(board, player);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldViolateOnYValueBelowPossible() {
        PlayerDto player = new PlayerDto("p", "p", 'O');
        Character[][] board = {
                new Character[]{'X', 'O', 'X'},
                new Character[]{'X', 'O', 'X'},
                new Character[]{'O', 'X', null}};
        when(playerClient.getMove(board, player))
                .thenReturn(just(of(0, -1)));
        ticTacToeGame.move(board, player);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldViolateOnYValueAbovePossible() {
        PlayerDto player = new PlayerDto("p", "p", 'O');
        Character[][] board = {
                new Character[]{'X', 'O', 'X'},
                new Character[]{'X', 'O', 'X'},
                new Character[]{'O', 'X', null}};
        when(playerClient.getMove(board, player))
                .thenReturn(just(of(0, 3)));
        ticTacToeGame.move(board, player);
    }

    @ParameterizedTest
    @MethodSource("providerForWinnerCheck")
    public void checkWinners(Character[][] board, boolean winner) {
        assertThat(ticTacToeGame.hasWinner(board), equalTo(winner));
    }
}