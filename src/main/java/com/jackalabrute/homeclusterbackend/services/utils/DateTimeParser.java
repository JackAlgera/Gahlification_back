package com.jackalabrute.homeclusterbackend.services.utils;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeParser {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public Instant parseDate(String date) {
        return LocalDateTime.parse(date, formatter).atZone(ZoneId.of("CET")).toInstant();
    }

}
