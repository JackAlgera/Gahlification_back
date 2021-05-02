package com.jackalabrute.WebServer.controllers;

import com.jackalabrute.WebServer.handlers.BaseHandler;
import com.jackalabrute.WebServer.handlers.TaskHandler;
import com.jackalabrute.WebServer.models.HelloMessage;
import com.jackalabrute.WebServer.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TaskController {

    @Autowired
    private TaskHandler taskHandler;

    /**
     * Returns all current tasks.
     * @return
     */
    @GetMapping(path = "/api/tasks")
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok(taskHandler.getTasks());
    }

}
