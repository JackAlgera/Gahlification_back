package com.jackalabrute.gahlification.controllers;

import com.jackalabrute.gahlification.database.models.TaskStep;
import com.jackalabrute.gahlification.database.models.tags.ETagName;
import com.jackalabrute.gahlification.database.models.tags.ETagType;
import com.jackalabrute.gahlification.database.models.tags.Tag;
import com.jackalabrute.gahlification.database.models.Task;
import com.jackalabrute.gahlification.services.TagService;
import com.jackalabrute.gahlification.services.TaskService;
import com.jackalabrute.gahlification.services.TaskStepService;
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

    @Autowired
    private TaskStepService taskStepService;

    /**
     * Clean database of all data.
     *
     * @return
     */
    @GetMapping(path = "/database/purge")
    public ResponseEntity<?> cleanDatabase() {
        tagService.deleteAllTags();
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

        Task task1 = taskService.createTask(new Task(null, "Task one", "Task with +1 days", Instant.now().plus(1, ChronoUnit.DAYS), 36000L, null, null));
        Task task2 = taskService.createTask(new Task(null, "Task two", "Task with +5 days", Instant.now().plus(5, ChronoUnit.DAYS), 36000L, null, null));
        Task task3 = taskService.createTask(new Task(null, "Task Three", "Task with +2 hours", Instant.now().plus(2, ChronoUnit.HOURS), 36000L, null, null));
        Task task4 = taskService.createTask(new Task(null, "Task four", "Task with -2 hours", Instant.now().minus(2, ChronoUnit.HOURS), 36000L, null, null));

        tagService.createTag(new Tag(null, task1.getTaskId(), ETagName.ADMIN.label, ETagType.TASK.label));
        tagService.createTag(new Tag(null, task1.getTaskId(), ETagName.URGENT.label, ETagType.TASK.label));
        tagService.createTag(new Tag(null, task2.getTaskId(), ETagName.GAHLOU.label, ETagType.TASK.label));
        tagService.createTag(new Tag(null, task3.getTaskId(), ETagName.ADMIN.label, ETagType.TASK.label));
        tagService.createTag(new Tag(null, task3.getTaskId(), ETagName.FLOKKIE.label, ETagType.TASK.label));
        tagService.createTag(new Tag(null, task4.getTaskId(), ETagName.URGENT.label, ETagType.TASK.label));

        taskStepService.createTaskStep(new TaskStep(null, task1.getTaskId(), "Step one completed", null, null));
        taskStepService.createTaskStep(new TaskStep(null, task1.getTaskId(), "Step two completed", null, null));
        taskStepService.createTaskStep(new TaskStep(null, task2.getTaskId(), "Step one completed", null, null));

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
