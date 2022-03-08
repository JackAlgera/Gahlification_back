package com.jackalabrute.gahlification.services;

import com.jackalabrute.gahlification.database.daos.TagDAO;
import com.jackalabrute.gahlification.database.models.tags.ETagType;
import com.jackalabrute.gahlification.database.models.tags.Tag;
import com.jackalabrute.gahlification.exceptions.TagTypeNotFoundException;
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
        tag.setTagId(idGenerator.getRandomId());

        return tagDAO.createTag(tag);
    }

    public void deleteTagByItemIdAndTagName(UUID itemId, String tagName) {
        if (ETagType.valueOf(tagName) == null) {
            throw new TagTypeNotFoundException(tagName);
        }

        taskService.findTaskById(itemId);
        tagDAO.deleteTagByItemIdAndTagName(itemId, tagName);
    }

    public void deleteTagsForItem(UUID itemId) {
        tagDAO.deleteTagsForItem(itemId);
    }

    public void deleteAllTags() {
        tagDAO.deleteAllTags();
    }

    public List<Tag> getTagsForItem(UUID itemId) {
        return tagDAO.getTagsForItemId(itemId);
    }

    public boolean checkTagForCreate(Tag tag) {
        return !tag.getTagName().isBlank() && !tag.getTagType().isBlank() && tag.getItemId() != null;
    }
}
