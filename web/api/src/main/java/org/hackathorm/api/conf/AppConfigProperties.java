package org.hackathorm.api.conf;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfigProperties {

    public static final String PUBSUB_NAME = "pubsub";
    public static final String GAME_MASTER_PLAYERS_TOPIC = "players";
    public static final String GAME_RESULTS_TOPIC = "gameResults";

}