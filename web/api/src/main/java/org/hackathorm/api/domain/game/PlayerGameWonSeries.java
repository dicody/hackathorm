package org.hackathorm.api.domain.game;

public class PlayerGameWonSeries extends PlayerGameSeries {
    private PlayerGameWonSeries(long value) {
        super(GameSeries.WON, value);
    }

    public static PlayerGameWonSeries won(long value) {
        return new PlayerGameWonSeries(value);
    }
}
