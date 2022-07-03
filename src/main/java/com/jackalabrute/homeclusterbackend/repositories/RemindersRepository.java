package com.jackalabrute.homeclusterbackend.repositories;

import com.jackalabrute.homeclusterbackend.models.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RemindersRepository extends JpaRepository<Reminder, UUID> {
}
