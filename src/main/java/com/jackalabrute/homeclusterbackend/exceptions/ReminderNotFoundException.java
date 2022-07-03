package com.jackalabrute.homeclusterbackend.exceptions;

import java.util.UUID;

public class ReminderNotFoundException extends RuntimeException {

    public ReminderNotFoundException(UUID taskId) {
        super(String.format("Task with id %s not found.", taskId));
    }
}
