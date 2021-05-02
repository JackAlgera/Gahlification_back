package com.jackalabrute.WebServer.handlers;

import com.jackalabrute.WebServer.models.Task;
import com.jackalabrute.WebServer.models.Time;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;

/**
 * Task handler
 */
@Component
public class TaskHandler {

    private final ArrayList<Task> tasks;

    public TaskHandler() {
        this.tasks = new ArrayList<>();

        this.tasks.add(new Task(Instant.parse("2021-05-02T13:00:11+00:00"), new Time(4, 0)));
        this.tasks.add(new Task(Instant.parse("2021-05-02T17:00:11+00:00"), new Time(2, 0)));
        this.tasks.add(new Task(Instant.parse("2021-05-02T20:00:11+00:00"), new Time(1, 0)));
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
