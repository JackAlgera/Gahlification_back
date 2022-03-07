package com.jackalabrute.gahlification.database.daos;

import com.jackalabrute.gahlification.database.models.Tag;
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
    public void deleteTagByTaskIdAndTagName(UUID taskId, String tagName) {
        tagRepository.deleteTagByTaskIdAndTagName(taskId, tagName);
    }

    @Override
    public List<Tag> getTagsForTask(UUID taskId) {
        return tagRepository.findTagsByTaskId(taskId);
    }
}
