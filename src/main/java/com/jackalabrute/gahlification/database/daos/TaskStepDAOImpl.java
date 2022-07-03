package com.jackalabrute.gahlification.database.daos;

import com.jackalabrute.gahlification.database.models.tasks.TaskStep;
import com.jackalabrute.gahlification.database.repos.TaskStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskStepDAOImpl implements TaskStepDAO {

    @Autowired
    private TaskStepRepository taskStepRepository;

    @Override
    public Optional<TaskStep> findById(UUID id) {
        return taskStepRepository.findById(id);
    }

    @Override
    public TaskStep create(TaskStep taskStep) {
        return taskStepRepository.save(taskStep);
    }

    @Override
    public void delete(UUID id) {
        taskStepRepository.deleteById(id);
    }

    @Override
    public List<TaskStep> findAll() {
        return taskStepRepository.findAll();
    }

    @Override
    public TaskStep update(TaskStep taskStep) {
        return taskStepRepository.save(taskStep);
    }

    @Override
    public List<TaskStep> findAllTaskStepsByTaskId(UUID taskId) {
        return taskStepRepository.findAllByTaskId(taskId);
    }
}
