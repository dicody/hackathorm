package org.hackathorm.api.domain.game;

import lombok.Data;

import java.util.stream.Collectors;

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
        gameResponse.id = String.valueOf(game.getSequenceNumber());
        gameResponse.info = game.getInfo();
        gameResponse.startedAt = ONLY_TIME_FORMAT.format(game.getStartedAt());
        game.getFinishedAt().map(ONLY_TIME_FORMAT::format).ifPresent(gameResponse::setFinishedAt);

        game.getWinner().map(GameResponse::toString).ifPresent(gameResponse::setWinner);
        gameResponse.players = game.getPlayers().stream()
                .map(GameResponse::toString)
                .collect(Collectors.joining(", "));
        return gameResponse;
    }

    private static String toString(PlayersVersion game) {
        return game.getPlayer().getName() + ":" + game.getImage().getName();
    }
}
