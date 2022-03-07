package com.jackalabrute.gahlification.database.daos;

import com.jackalabrute.gahlification.database.models.tags.Tag;

import java.util.List;
import java.util.UUID;

public interface TagDAO {

    Tag createTag(Tag tag);
    void deleteTagByItemIdAndTagName(UUID itemId, String tagName);
    void deleteTagsForItem(UUID itemId);
    List<Tag> getTagsForItemId(UUID itemId);
}
