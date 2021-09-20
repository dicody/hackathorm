package org.hackathorm.api.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateServiceTest {
    @Test
    void getCurrentDate() {
        assertNotNull(new DateService().getCurrentDate());
    }
}