package org.hackathorm.api.conf;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Formatters {
    public static final DateFormat ONLY_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
}
