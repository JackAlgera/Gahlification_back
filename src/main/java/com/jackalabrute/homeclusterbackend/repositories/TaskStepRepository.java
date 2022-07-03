package com.jackalabrute.homeclusterbackend.repositories;

import com.jackalabrute.homeclusterbackend.models.tasks.TaskStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskStepRepository extends JpaRepository<TaskStep, UUID> {
    List<TaskStep> findAllByTaskId(UUID taskId);
}
