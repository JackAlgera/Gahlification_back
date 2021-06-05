package com.jackalabrute.WebServer.handlers;

import com.jackalabrute.WebServer.models.Task;
import com.jackalabrute.WebServer.models.Timestamp;
import com.jackalabrute.WebServer.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Task handler
 */
@Service
public class TaskHandler {

    @Autowired
    private IdGenerator idGenerator;

    private final HashMap<UUID, Task> tasks;

    public TaskHandler() {
        this.tasks = new HashMap<>();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTask(UUID taskid) {
        return tasks.get(taskid);
    }

    public Task removeTask(UUID taskId) {
        return tasks.remove(taskId);
    }

    public Task addTask(String taskName, Instant timestamp, Timestamp delay) {
        Task newTask = new Task(taskName, timestamp, delay, idGenerator.getRandomId());
        tasks.put(newTask.getTaskId(), newTask);

        return newTask;
    }
}
