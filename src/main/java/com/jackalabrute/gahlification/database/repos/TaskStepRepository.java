package com.jackalabrute.gahlification.database.repos;

import com.jackalabrute.gahlification.database.models.TaskStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskStepRepository extends JpaRepository<TaskStep, UUID> {
    List<TaskStep> findAllByTaskId(UUID taskId);
}
