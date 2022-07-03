package com.jackalabrute.gahlification.components.reminders;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RemindersRepository extends JpaRepository<Reminder, UUID> {
}
