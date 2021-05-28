package com.jackalabrute.WebServer.controllers;

import com.jackalabrute.WebServer.handlers.TaskHandler;
import com.jackalabrute.WebServer.models.Task;
import com.jackalabrute.WebServer.statuscodes.NotFoundException;
import com.jackalabrute.WebServer.utils.DateTimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TaskController {

    @Autowired
    private TaskHandler taskHandler;

    @Autowired
    private DateTimeParser dateTimeParser;

    /**
     * Returns all current tasks.
     * @return
     */
    @GetMapping(path = "/api/tasks")
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok(taskHandler.getTasks());
    }

    /**
     * Delete task with given Id.
     * @param taskId as UUID
     * @return
     */
    @DeleteMapping(path = "/api/tasks/{taskId}")
    public ResponseEntity<Task> deleteTask(@PathVariable String taskId) {
        Task task = taskHandler.removeTask(UUID.fromString(taskId));

        if(task == null) {
            throw new NotFoundException(String.format("Task with Id %s not found", taskId));
        }

        return ResponseEntity.ok(task);
    }

    @PostMapping(path = "/api/tasks")
    public ResponseEntity<Task> addTask(
            @RequestParam(required = true, name = "taskName") String taskName,
            @RequestParam(required = true, name = "startDate") String startDate,
            @RequestParam(required = true, name = "delayHours") Integer delayHours,
            @RequestParam(required = true, name = "delayMinutes") Integer delayMinutes
            ) {
        Task task = taskHandler.addTask(taskName, dateTimeParser.parseDate(startDate), delayHours, delayMinutes);

        return ResponseEntity.ok(task);
    }
}
