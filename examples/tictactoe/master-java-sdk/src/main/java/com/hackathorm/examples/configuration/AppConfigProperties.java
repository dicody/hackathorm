package com.hackathorm.examples.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfigProperties {

    @Value("${admin.gameresults.topic:gameResults}")
    private String gameResultsTopic;
    @Value("${pubsub.name:pubsub}")
    private String pubsubName;

}
