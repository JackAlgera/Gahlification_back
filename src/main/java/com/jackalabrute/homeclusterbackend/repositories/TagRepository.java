package com.jackalabrute.homeclusterbackend.repositories;

import com.jackalabrute.homeclusterbackend.models.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findTagsByItemId(UUID itemId);
    void deleteTagByItemIdAndTagName(UUID itemId, String tagName);
    void deleteTagsByItemId(UUID itemId);
}
