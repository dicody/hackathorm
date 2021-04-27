package com.hackathorm.examples.service;

import com.hackathorm.examples.domain.PlayerDto;
import com.hackathorm.examples.integration.PlayerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple2;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
@RequiredArgsConstructor
public class TicTacToeGame {

    private final PlayerClient playerClient;
    private final int COUNT = 3;

    public Optional<PlayerDto> runGame(PlayerDto player1, PlayerDto player2) {
        player1.setSign('X');
        player2.setSign('O');

        Character[][] board = new Character[COUNT][COUNT];

        PlayerDto winner = null;
        do {
            board = move(board, player1);
            if (hasWinner(board)) {
                winner = player1;
            }
            if (winner == null && nextMovePossible(board)) {
                board = move(board, player2);
                if (hasWinner(board)) {
                    winner = player2;
                }
            }
        } while (winner == null && nextMovePossible(board));

        return Optional.ofNullable(winner);
    }

    Character[][] move(Character[][] board, PlayerDto player) {
        Tuple2<Integer, Integer> move = null;
        try {
            move = playerClient.getMove(board, player).blockOptional().orElseThrow();
            return validateBoards(board, player, move);
        } catch (IllegalArgumentException e) {
            log.error("Player: {} has violated the rules! Original board: {} and players move: {}", player, board, move);
            throw e;
        }
    }

    private boolean nextMovePossible(Character[][] board) {
        return Arrays.stream(board).flatMap(Arrays::stream).anyMatch(Objects::isNull);
    }

    private Character[][] validateBoards(Character[][] orig, PlayerDto player, Tuple2<Integer, Integer> move) {
        checkArgument(move.getT1() >= 0 && move.getT1() < COUNT, "x value must be between [0;2]");
        checkArgument(move.getT2() >= 0 && move.getT2() < COUNT, "y value must be between [0;2]");
        checkArgument(orig[move.getT1()][move.getT2()] == null,
                "invalid coordinates provided, value already exists in cell:" + move);

        Character[][] withMove = Arrays.copyOf(orig, orig.length);
        withMove[move.getT1()][move.getT2()] = player.getSign();
        return withMove;
    }

    boolean hasWinner(Character[][] board) {
        return anyMatchInRow(board) || anyMatchInColumns(board) || anyMatchInDiagonals(board);
    }

    boolean anyMatchInRow(Character[][] board) {
        return Arrays.stream(board).anyMatch(this::allMatch);
    }

    boolean anyMatchInColumns(Character[][] board) {
        return anyMatchInRow(transposeBoard(board));
    }

    private Character[][] transposeBoard(Character[][] b) {
        Character[][] temp = new Character[COUNT][COUNT];
        for (int i = 0; i < b.length; i++)
            for (int j = 0; j < b[0].length; j++)
                temp[j][i] = b[i][j];
        return temp;
    }

    private boolean anyMatchInDiagonals(Character[][] board) {
        return allMatch(board[0][0], board[1][1], board[2][2])
                || allMatch(board[0][2], board[1][1], board[2][0]);
    }

    private boolean allMatch(Character... chars) {
        return allMatch(Arrays.asList(chars));
    }

    private boolean allMatch(List<Character> chars) {
        return chars.stream().filter(Objects::nonNull).anyMatch(ch -> Collections.frequency(chars, ch) == 3);
    }
}
