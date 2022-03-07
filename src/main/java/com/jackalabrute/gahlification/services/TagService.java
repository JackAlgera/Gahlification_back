package com.jackalabrute.gahlification.services;

import com.jackalabrute.gahlification.database.daos.TagDAO;
import com.jackalabrute.gahlification.database.models.Tag;
import com.jackalabrute.gahlification.database.models.Task;
import com.jackalabrute.gahlification.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagService {

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdGenerator idGenerator;

    public Tag createTag(Tag tag) {
        taskService.findTaskById(tag.getTaskId());
        tag.setTagId(idGenerator.getRandomId());

        System.out.println(tag);

        return tagDAO.createTag(tag);
    }

    public void deleteTagByTaskIdAndTagName(UUID taskId, String tagName) {
        taskService.findTaskById(taskId);
        tagDAO.deleteTagByTaskIdAndTagName(taskId, tagName);
    }

    public List<Tag> getTagsForTask(UUID taskId) {
        Task task = taskService.findTaskById(taskId);
        return tagDAO.getTagsForTask(task.getTaskId());
    }

    public boolean checkTagForCreate(Tag tag) {
        return !tag.getTagName().isBlank() && tag.getTaskId() != null;
    }
}
