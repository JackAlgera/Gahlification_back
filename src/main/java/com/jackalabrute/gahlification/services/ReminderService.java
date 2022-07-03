package com.jackalabrute.gahlification.services;

import com.jackalabrute.gahlification.database.models.Reminder;
import com.jackalabrute.gahlification.database.repos.RemindersRepository;
import com.jackalabrute.gahlification.exceptions.ReminderNotFoundException;
import com.jackalabrute.gahlification.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ReminderService {

    @Autowired
    private RemindersRepository remindersRepository;

    @Autowired
    private IdGenerator idGenerator;

    public Reminder createReminder(Reminder reminder) {
        reminder.setReminderId(idGenerator.getRandomId());
        Instant now = idGenerator.now();
        reminder.setCreatedOn(now);
        reminder.setLastModified(now);

        if (reminder.getStartNotifyingDate() == null) {
            reminder.setStartNotifyingDate(reminder.getDueDate());
        }

        if (reminder.getPingFrequencyInterval() == null) {
            reminder.setPingFrequencyInterval(3600L);
        }

        return remindersRepository.save(reminder);
    }

    public void deleteReminder(UUID reminderId) {
        Reminder reminder = findReminderById(reminderId);
        remindersRepository.deleteById(reminder.getReminderId());
    }

    public Reminder findReminderById(UUID reminderId) {
        return remindersRepository.findById(reminderId)
                      .orElseThrow(() -> new ReminderNotFoundException(reminderId));
    }

    public List<Reminder> findAllReminders() {
        return remindersRepository.findAll();
    }

    public Reminder updateReminder(Reminder reminder) {
        Reminder currentReminder = findReminderById(reminder.getReminderId());
        currentReminder.updateValues(reminder);
        
        return remindersRepository.save(currentReminder);
    }

    public boolean checkReminderForUpdate(Reminder reminder) {
        return checkReminderForCreate(reminder) && reminder.getReminderId() != null;
    }

    public boolean checkReminderForCreate(Reminder reminder) {
        return reminder.getName() != null &&
               reminder.getDueDate() != null;
    }
}
