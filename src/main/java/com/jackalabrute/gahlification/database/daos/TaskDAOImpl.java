package com.jackalabrute.gahlification.database.daos;

import com.jackalabrute.gahlification.database.models.Task;
import com.jackalabrute.gahlification.database.repos.TaskRepository;
import com.jackalabrute.gahlification.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskDAOImpl implements DefaultDAO<Task> {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TagService tagService;

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
        tagService.deleteTagsForItem(id);
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
