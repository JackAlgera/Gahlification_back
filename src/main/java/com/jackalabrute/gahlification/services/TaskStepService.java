package com.jackalabrute.gahlification.services;

import com.jackalabrute.gahlification.database.daos.TaskStepDAOImpl;
import com.jackalabrute.gahlification.database.models.Task;
import com.jackalabrute.gahlification.database.models.TaskStep;
import com.jackalabrute.gahlification.exceptions.TaskNotFoundException;
import com.jackalabrute.gahlification.exceptions.TaskStepNotFoundException;
import com.jackalabrute.gahlification.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TaskStepService {

    @Autowired
    private TaskStepDAOImpl taskStepDAO;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdGenerator idGenerator;

    public TaskStep createTaskStep(TaskStep taskStep) {
        taskStep.setTaskStepId(idGenerator.getRandomId());
        Instant now = idGenerator.now();
        taskStep.setCreatedOn(now);
        taskStep.setLastModified(now);

        return taskStepDAO.create(taskStep);
    }

    public void deleteTaskStep(UUID taskStepId) {
        TaskStep taskStep = findTaskStepById(taskStepId);
        taskStepDAO.delete(taskStep.getTaskId());
    }

    public TaskStep findTaskStepById(UUID taskStepId) {
        return taskStepDAO.findById(taskStepId)
                      .orElseThrow(() -> new TaskStepNotFoundException(taskStepId));
    }

    public List<TaskStep> findAllTaskStepsByTaskId(UUID taskId) {
        return taskStepDAO.findAllTaskStepsByTaskId(taskId);
    }

    public boolean checkTaskStepForCreate(TaskStep taskStep) {
        return taskStep.getDescription() != null && taskStep.getTaskId() != null;
    }
}
