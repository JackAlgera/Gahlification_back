package com.jackalabrute.homeclusterbackend.repositories;

import com.jackalabrute.homeclusterbackend.models.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
}
