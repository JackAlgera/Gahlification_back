package com.jackalabrute.gahlification.database.daos;

import com.jackalabrute.gahlification.database.models.tags.Tag;
import com.jackalabrute.gahlification.database.repos.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TagDAOImpl implements TagDAO {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTagByItemIdAndTagName(UUID itemId, String tagName) {
        tagRepository.deleteTagByItemIdAndTagName(itemId, tagName);
    }

    @Override
    public void deleteTagsForItem(UUID itemId) {
        tagRepository.deleteTagsByItemId(itemId);
    }

    @Override
    public void deleteAllTags() {
        tagRepository.deleteAll();
    }

    @Override
    public List<Tag> getTagsForItemId(UUID itemId) {
        return tagRepository.findTagsByItemId(itemId);
    }
}
