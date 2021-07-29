package com.jackalabrute.WebServer.handlers;

import com.jackalabrute.WebServer.models.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Task handler interface
 */
public interface TaskHandler {

    ArrayList<Task> getTasks();

    Task getTask(UUID taskId);

    Task removeTask(UUID taskId);

    Task addTask(String taskName, Instant timestamp, Long repeatDelay);

}
