package com.jackalabrute.homeclusterbackend.services;

import com.jackalabrute.homeclusterbackend.models.tags.ETagType;
import com.jackalabrute.homeclusterbackend.models.tags.Tag;
import com.jackalabrute.homeclusterbackend.repositories.TagRepository;
import com.jackalabrute.homeclusterbackend.exceptions.TagTypeNotFoundException;
import com.jackalabrute.homeclusterbackend.services.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdGenerator idGenerator;

    public Tag createTag(Tag tag) {
        tag.setTagId(idGenerator.getRandomId());

        return tagRepository.save(tag);
    }

    public void deleteTagByItemIdAndTagName(UUID itemId, String tagName) {
        if (ETagType.valueOf(tagName) == null) {
            throw new TagTypeNotFoundException(tagName);
        }

        taskService.findTaskById(itemId);
        tagRepository.deleteTagByItemIdAndTagName(itemId, tagName);
    }

    public void deleteTagsForItem(UUID itemId) {
        tagRepository.deleteTagsByItemId(itemId);
    }

    public void deleteAllTags() {
        tagRepository.deleteAll();
    }

    public List<Tag> getTagsForItem(UUID itemId) {
        return tagRepository.findTagsByItemId(itemId);
    }

    public boolean checkTagForCreate(Tag tag) {
        return !tag.getTagName().isBlank() && !tag.getTagType().isBlank() && tag.getItemId() != null;
    }
}
