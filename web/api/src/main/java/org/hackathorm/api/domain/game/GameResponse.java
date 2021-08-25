package org.hackathorm.api.domain.game;

import lombok.Data;
import org.hackathorm.api.domain.image.SolutionImage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hackathorm.api.conf.Formatters.ONLY_TIME_FORMAT;

@Data
public class GameResponse {
    private String id;
    private String winner;
    private String players;
    private String info;
    private String startedAt;
    private String finishedAt;

    public static GameResponse from(Game game) {
        GameResponse gameResponse = new GameResponse();
        gameResponse.id = String.valueOf(game.getNumber());
        gameResponse.info = game.getInfo();
        gameResponse.startedAt = ONLY_TIME_FORMAT.format(game.getStartedAt());
        gameResponse.finishedAt = game.getFinishedAt().map(ONLY_TIME_FORMAT::format).orElse("Not finished yet..");
        gameResponse
                .setWinner(game.getFinishedAt()
                        .map(gameFinished -> game.getWinner().orElse("Tie"))
                        .orElse("No winner yet.."));
        gameResponse.players = Stream.of(game.getPlayer1(), game.getPlayer2())
                .map(GameResponse::imageToString)
                .collect(Collectors.joining(", "));
        return gameResponse;
    }

    private static String imageToString(SolutionImage game) {
        return game.getSubmittedBy().getName() + ":" + game.getName();
    }
}
