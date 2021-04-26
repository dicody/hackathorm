package com.hackathorm.examples;

import com.hackathorm.examples.domain.GameMoveRequest;
import com.hackathorm.examples.domain.GameMoveResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class GameController {

    static final String MOVE_PATH = "/move";

    private final Random random = new Random();

    @PostMapping(path = MOVE_PATH)
    public Mono<GameMoveResponse> handleNextMove(@RequestBody GameMoveRequest request) {
        return Mono.fromSupplier(() -> {
            log.info("got request: {}", request);

            if (!containsEmptyPosition(request.getBoard())) {
                throw new IllegalStateException("No empty place on a board!");
            }

            GameMoveResponse move = getRandomEmptyPosition(request.getBoard());
            log.info("move: {}", move);
            return move;
        });
    }

    private boolean containsEmptyPosition(List<List<Character>> board) {
        log.info("board: \n{}", board.stream()
                .map(row -> row.stream().map(sign -> "|" + sign + "|").collect(Collectors.joining()))
                .collect(Collectors.joining("\n")));
        return board.stream().anyMatch(row -> row.stream().anyMatch(Objects::isNull));
    }

    private GameMoveResponse getRandomEmptyPosition(List<List<Character>> board) {
        for (int x = random.nextInt(3); x < board.size(); x++) {
            for (int y = random.nextInt(3); y < board.get(x).size(); y++) {
                if (board.get(x).get(y) == null) {
                    GameMoveResponse move = new GameMoveResponse();
                    move.setX(x);
                    move.setY(y);
                    return move;
                }
            }
        }
        return getRandomEmptyPosition(board);
    }
}
