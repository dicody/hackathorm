package service;

import domain.Player;
import integration.PlayerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final PlayerClient playerClient;
    private final int COUNT = 3;

    public Optional<Player> runGame(Player player1, Player player2) {
        player1.setSign('X');
        player2.setSign('O');

        Character[][] board = new Character[COUNT][COUNT];

        Player winner = null;
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

    Character[][] move(Character[][] board, Player player) {
        Character[][] move = null;
        try {
            move = playerClient.getMove(board, player);
            return validateBoards(board, move);
        } catch (IllegalArgumentException e) {
            log.error("Player: {} has violated the rules! Original board: {} and players move: {}", player, board, move);
            throw e;
        }
    }

    private boolean nextMovePossible(Character[][] board) {
        return Arrays.stream(board).flatMap(Arrays::stream).anyMatch(Objects::isNull);
    }

    private Character[][] validateBoards(Character[][] orig, Character[][] withMove) {
        IllegalArgumentException ex = new IllegalArgumentException("The board returned from player is not valid!");

        List<Character> newBoard = Arrays.stream(withMove).flatMap(Arrays::stream).collect(Collectors.toList());
        List<Character> origBoard = Arrays.stream(orig).flatMap(Arrays::stream).collect(Collectors.toList());

        if (newBoard.size() != origBoard.size()) throw ex;

        int count = 0;
        for (int i = 0; i < newBoard.size(); i++) {
            if (!Objects.equals(newBoard.get(i), origBoard.get(i))) {
                count++;
            }
        }

        if (count != 1) throw ex;

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
