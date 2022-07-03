package com.jackalabrute.gahlification.database.daos;

import com.jackalabrute.gahlification.database.models.tasks.Task;
import com.jackalabrute.gahlification.database.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskDAOImpl implements DefaultDAO<Task> {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Optional<Task> findById(UUID id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void delete(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task update(Task task) {
        return taskRepository.save(task);
    }
}
