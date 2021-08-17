package com.hackathorm.examples.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfigProperties {

    public static final String PUBSUB_NAME = "${pubsub.name:pubsub}";

    @Value("${admin.gameresults.topic:gameResults}")
    private String gameResultsTopic;
    @Value(PUBSUB_NAME)
    private String pubsubName;

}
