package com.jackalabrute.gahlification.database.daos;

import com.jackalabrute.gahlification.database.models.Tag;

import java.util.List;
import java.util.UUID;

public interface TagDAO {

    Tag createTag(Tag tag);
    void deleteTagByTaskIdAndTagName(UUID taskId, String tagName);
    List<Tag> getTagsForTask(UUID taskId);
}
