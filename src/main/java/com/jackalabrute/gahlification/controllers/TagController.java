package com.jackalabrute.gahlification.controllers;

import com.jackalabrute.gahlification.database.models.tags.Tag;
import com.jackalabrute.gahlification.exceptions.TagTypeNotFoundException;
import com.jackalabrute.gahlification.exceptions.statuscodes.IncorrectRequestException;
import com.jackalabrute.gahlification.exceptions.statuscodes.NotFoundException;
import com.jackalabrute.gahlification.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class TagController {
    
    @Autowired
    private TagService tagService;

    /**
     * Get all tags for item.
     *
     * @param itemId
     * @return
     */
    @GetMapping(path = "/tags")
    public ResponseEntity<List<Tag>> getAllTagsForTask(@RequestParam String itemId) {
        try {
            List<Tag> tags = tagService.getTagsForItem(UUID.fromString(itemId));
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", itemId));
        }
    }

    /**
     * Generates a list of tags for item.
     *
     * @param tags
     * @return
     */
    @PostMapping(path = "/tags")
    public ResponseEntity<List<Tag>> addTagsToTask(@RequestBody List<Tag> tags) {
        try {
            tags.forEach(tag -> {
                if (!tagService.checkTagForCreate(tag)) {
                    throw new IllegalArgumentException();
                }
            });

            List<Tag> newTags = tags.stream().map(tag -> tagService.createTag(tag)).collect(Collectors.toList());
            return new ResponseEntity<>(newTags, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException("Incorrect task format, missing parameters.");
        }
    }

    /**
     * Deletes a tag from an item.
     *
     * @param itemId
     * @param tagName
     * @return
     */
    @DeleteMapping(path = "/tags")
    public ResponseEntity<?> deleteTask(@RequestParam String itemId, @RequestParam String tagName) {
        try {
            tagService.deleteTagByItemIdAndTagName(UUID.fromString(itemId), tagName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TagTypeNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }  catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", itemId));
        }
    }
}
