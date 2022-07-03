package com.jackalabrute.gahlification.services;

import com.jackalabrute.gahlification.database.daos.TaskDAOImpl;
import com.jackalabrute.gahlification.exceptions.TaskNotFoundException;
import com.jackalabrute.gahlification.database.models.tasks.Task;
import com.jackalabrute.gahlification.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskDAOImpl taskDAO;

    @Autowired
    private IdGenerator idGenerator;

    public Task createTask(Task task) {
        task.setTaskId(idGenerator.getRandomId());
        Instant now = idGenerator.now();
        task.setCreatedOn(now);
        task.setLastModified(now);

        if (task.getDoTaskAtDate() == null) {
            task.setDoTaskAtDate(Instant.now().plus(1, ChronoUnit.HOURS));
        }

        return taskDAO.create(task);
    }

    public void deleteTask(UUID taskId) {
        Task task = findTaskById(taskId);
        taskDAO.delete(task.getTaskId());
    }

    public Task findTaskById(UUID taskId) {
        return taskDAO.findById(taskId)
                      .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    public List<Task> findAllTasks() {
        return taskDAO.findAll();
    }

    public Task updateTask(Task task) {
        Task currentTask = findTaskById(task.getTaskId());
        currentTask.updateValues(task);
        
        return taskDAO.update(currentTask);
    }

    public boolean checkTaskForUpdate(Task task) {
        return checkTaskForCreate(task) && task.getTaskId() != null;
    }

    public boolean checkTaskForCreate(Task task) {
        return task.getTaskName() != null && task.getRepeatDelay() != null; // task.getDoTaskAtDate() != null
    }
}
