package com.jackalabrute.gahlification.controllers;

import com.jackalabrute.gahlification.database.models.tags.ETagName;
import com.jackalabrute.gahlification.database.models.tags.ETagType;
import com.jackalabrute.gahlification.database.models.tags.Tag;
import com.jackalabrute.gahlification.database.models.Task;
import com.jackalabrute.gahlification.services.TagService;
import com.jackalabrute.gahlification.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
public class TestController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TagService tagService;

    /**
     * Clean database of all data.
     *
     * @return
     */
    @GetMapping(path = "/database/purge")
    public ResponseEntity<?> cleanDatabase() {
        for (Task task : taskService.findAllTasks()) {
            taskService.deleteTask(task.getTaskId());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Clean database of all data and add test data.
     *
     * @return
     */
    @GetMapping(path = "/database/add-test-data")
    public ResponseEntity<?> cleanAndAddTestData() {
        cleanDatabase();

        Task task1 = taskService.createTask(new Task(null, "Task one", "Some description", Instant.now().plus(1, ChronoUnit.DAYS), 36000L, null, null));
        Task task2 = taskService.createTask(new Task(null, "Task two", "Some other description", Instant.now().plus(5, ChronoUnit.DAYS), 36000L, null, null));
        Task task3 = taskService.createTask(new Task(null, "Task Three", "Do this thing", Instant.now().plus(2, ChronoUnit.HOURS), 36000L, null, null));

        tagService.createTag(new Tag(null, task1.getTaskId(), ETagName.ADMIN.label, ETagType.TASK.label));
        tagService.createTag(new Tag(null, task1.getTaskId(), ETagName.URGENT.label, ETagType.TASK.label));
        tagService.createTag(new Tag(null, task2.getTaskId(), ETagName.GAHLOU.label, ETagType.TASK.label));
        tagService.createTag(new Tag(null, task3.getTaskId(), ETagName.ADMIN.label, ETagType.TASK.label));
        tagService.createTag(new Tag(null, task3.getTaskId(), ETagName.FLOKKIE.label, ETagType.TASK.label));

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
