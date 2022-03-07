package com.jackalabrute.gahlification.database.repos;

import com.jackalabrute.gahlification.database.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findTagsByTaskId(UUID taskId);
    void deleteTagByTaskIdAndTagName(UUID taskId, String tagName);
}
