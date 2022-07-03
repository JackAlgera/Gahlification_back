package com.jackalabrute.homeclusterbackend.exceptions;

import java.util.UUID;

public class TaskStepNotFoundException extends RuntimeException {

    public TaskStepNotFoundException(UUID taskId) {
        super(String.format("Task step with id %s not found.", taskId));
    }
}
