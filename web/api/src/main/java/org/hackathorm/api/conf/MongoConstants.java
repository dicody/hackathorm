package org.hackathorm.api.conf;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MongoConstants {
    // Feedbacks
    public static final String FEEDBACKS_COLLECTION_NAME = "feedbacks";
    // Images
    public static final String IMAGES_COLLECTION_NAME = "images";
    // Games
    public static final String GAMES_COLLECTION_NAME = "games";
}
