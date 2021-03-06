package org.hackathorm.api.domain.game;

import lombok.Data;
import org.hackathorm.api.domain.image.SolutionImage;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hackathorm.api.conf.MongoConstants.GAMES_COLLECTION_NAME;

@Data
@Document(collection = GAMES_COLLECTION_NAME)
public class Game {
    @Id
    private String id;
    private long number;
    private String winnerId;
    private List<SolutionImage> players;
    private String info;
    private Date startedAt;
    private Date finishedAt;

    public Optional<String> getWinner() {
        return Optional.ofNullable(winnerId);
    }

    public Optional<Date> getFinishedAt() {
        return Optional.ofNullable(finishedAt);
    }
}
