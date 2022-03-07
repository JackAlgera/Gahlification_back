package com.jackalabrute.gahlification.exceptions;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(UUID taskId) {
        super(String.format("Task with id %s not found.", taskId));
    }
}
