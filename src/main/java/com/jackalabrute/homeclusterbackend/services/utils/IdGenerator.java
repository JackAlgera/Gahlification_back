package com.jackalabrute.homeclusterbackend.services.utils;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class IdGenerator {

    public UUID getRandomId() {
        return UUID.randomUUID();
    }

    public Instant now() {
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }

}
