package com.jackalabrute.gahlification.controllers;

import com.jackalabrute.gahlification.database.models.Tag;
import com.jackalabrute.gahlification.exceptions.TaskNotFoundException;
import com.jackalabrute.gahlification.exceptions.statuscodes.IncorrectRequestException;
import com.jackalabrute.gahlification.exceptions.statuscodes.NotFoundException;
import com.jackalabrute.gahlification.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TagController {
    
    @Autowired
    private TagService tagService;

    /**
     * Get all tags for task.
     *
     * @return
     */
    @GetMapping(path = "/tasks/{taskId}/tags")
    public ResponseEntity<List<Tag>> getAllTagsForTask(@PathVariable String taskId) {
        try {
            List<Tag> tags = tagService.getTagsForTask(UUID.fromString(taskId));
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", taskId));
        }
    }

    /**
     * Generates a new tag for task.
     *
     * @param tag
     * @return
     */
    @PostMapping(path = "/tags")
    public ResponseEntity<Tag> addTagToTask(@RequestBody Tag tag) {
        try {
            if (!tagService.checkTagForCreate(tag)) {
                throw new IllegalArgumentException();
            }

            Tag newTag = tagService.createTag(tag);
            return new ResponseEntity<>(newTag, HttpStatus.CREATED);
        } catch (TaskNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException("Incorrect task format, missing parameters.");
        }
    }

    /**
     * Deletes a task.
     *
     * @param taskId
     * @return
     */
    @DeleteMapping(path = "/tasks/{taskId}/tags/{tagName}")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId, @PathVariable String tagName) {
        try {
            tagService.deleteTagByTaskIdAndTagName(UUID.fromString(taskId), tagName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", taskId));
        }
    }
}
