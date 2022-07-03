package com.jackalabrute.gahlification.database.repos;

import com.jackalabrute.gahlification.database.models.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RemindersRepository extends JpaRepository<Reminder, UUID> {
}
