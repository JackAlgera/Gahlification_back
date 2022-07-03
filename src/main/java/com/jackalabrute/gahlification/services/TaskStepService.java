package com.jackalabrute.gahlification.services;

import com.jackalabrute.gahlification.database.models.tasks.TaskStep;
import com.jackalabrute.gahlification.database.repos.TaskStepRepository;
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
    private TaskStepRepository taskStepRepository;

    @Autowired
    private IdGenerator idGenerator;

    public TaskStep createTaskStep(TaskStep taskStep) {
        taskStep.setTaskStepId(idGenerator.getRandomId());
        Instant now = idGenerator.now();
        taskStep.setCreatedOn(now);
        taskStep.setLastModified(now);

        return taskStepRepository.save(taskStep);
    }

    public void deleteTaskStep(UUID taskStepId) {
        TaskStep taskStep = findTaskStepById(taskStepId);
        taskStepRepository.deleteById(taskStep.getTaskId());
    }

    public TaskStep findTaskStepById(UUID taskStepId) {
        return taskStepRepository.findById(taskStepId)
                      .orElseThrow(() -> new TaskStepNotFoundException(taskStepId));
    }

    public List<TaskStep> findAllTaskStepsByTaskId(UUID taskId) {
        return taskStepRepository.findAllByTaskId(taskId);
    }

    public boolean checkTaskStepForCreate(TaskStep taskStep) {
        return taskStep.getDescription() != null && taskStep.getTaskId() != null;
    }
}
