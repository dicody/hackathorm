package org.hackathorm.api.domain.game;

public class PlayerGameLostSeries extends PlayerGameSeries {
    private PlayerGameLostSeries(long value) {
        super(GameSeries.LOST, value);
    }

    public static PlayerGameLostSeries lost(long value) {
        return new PlayerGameLostSeries(value);
    }
}
