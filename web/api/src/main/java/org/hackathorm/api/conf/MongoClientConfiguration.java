package org.hackathorm.api.conf;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Slf4j
@Configuration
public class MongoClientConfiguration {
    @Bean
    public MongoClient reactiveMongoClientFactoryBean(@Value("${mongodb.connectionString}") String connectionString) {
        log.debug("mongoDB connection string: {}", connectionString);
        return MongoClients.create(connectionString);
    }

    @Bean
    public ReactiveMongoTemplate mongoTemplate(MongoClient client,
                                               @Value("${mongodb.database}") String database) {
        log.info("mongoDB will use: {}", database);
        return new ReactiveMongoTemplate(client, database);
    }
}
