package com.jackalabrute.WebServer.handlers;

import com.jackalabrute.WebServer.models.Task;
import com.jackalabrute.WebServer.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Task handler implementation
 */
@Service
public class TaskHandlerImpl implements TaskHandler {

    @Autowired
    private IdGenerator idGenerator;

    private final HashMap<UUID, Task> tasks;

    public TaskHandlerImpl() {
        this.tasks = new HashMap<>();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTask(UUID taskId) {
        return tasks.get(taskId);
    }

    public Task removeTask(UUID taskId) {
        return tasks.remove(taskId);
    }

    public Task addTask(String taskName, Instant doTaskAtDate, Long repeatDelay) {
        Task newTask = new Task(taskName, idGenerator.getRandomId(), doTaskAtDate, repeatDelay);
        tasks.put(newTask.getTaskId(), newTask);

        return newTask;
    }

    public Task updateTask(UUID taskId, Task updatedTask) {
        if (tasks.get(taskId) == null) {
            return null;
        } else {
            updatedTask.setTaskId(taskId);
            tasks.put(taskId, updatedTask);
        }
        return tasks.get(taskId);
    }

}
