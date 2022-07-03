package com.jackalabrute.gahlification.components.reminders;

import com.jackalabrute.gahlification.exceptions.ReminderNotFoundException;
import com.jackalabrute.gahlification.exceptions.statuscodes.IncorrectRequestException;
import com.jackalabrute.gahlification.exceptions.statuscodes.NotFoundException;
import com.jackalabrute.gahlification.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ReminderController {
    
    @Autowired
    private ReminderService reminderService;

    @Autowired
    private TagService tagService;

    /**
     * Get reminder with provided reminderId.
     *
     * @param reminderId
     * @return
     */
    @GetMapping(path = "/reminders/{reminderId}")
    public ResponseEntity<Reminder> getReminderById(@PathVariable String reminderId) {
        try {
            Reminder reminder = reminderService.findReminderById(UUID.fromString(reminderId));
            return new ResponseEntity<>(reminder, HttpStatus.OK);
        } catch (ReminderNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", reminderId));
        }
    }

    /**
     * Get all reminders.
     *
     * @return
     */
    @GetMapping(path = "/reminders")
    public ResponseEntity<List<Reminder>> getAllReminders() {
        return new ResponseEntity<>(reminderService.findAllReminders(), HttpStatus.OK);
    }

    /**
     * Generates a new reminder.
     *
     * @param reminder
     * @return
     */
    @PostMapping(path = "/reminders")
    public ResponseEntity<Reminder> addReminder(@RequestBody Reminder reminder) {
        try {
            if (!reminderService.checkReminderForCreate(reminder)) {
                throw new IllegalArgumentException();
            }

            Reminder newReminder = reminderService.createReminder(reminder);
            return new ResponseEntity<>(newReminder, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException("Incorrect reminder format, missing parameters.");
        }
    }

    /**
     * Updates a reminder.
     *
     * @param reminder
     * @return
     */
    @PutMapping(path = "/reminders")
    public ResponseEntity<Reminder> updateReminder(@RequestBody Reminder reminder) {
        try {
            if (!reminderService.checkReminderForUpdate(reminder)) {
                throw new IllegalArgumentException();
            }

            Reminder updatedReminder = reminderService.updateReminder(reminder);
            return new ResponseEntity<>(updatedReminder, HttpStatus.OK);
        } catch (ReminderNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException("Incorrect reminder format, missing parameters.");
        }
    }

    /**
     * Deletes a reminder.
     *
     * @param reminderId
     * @return
     */
    @DeleteMapping(path = "/reminders/{reminderId}")
    public ResponseEntity<?> deleteReminder(@PathVariable String reminderId) {
        try {
            Reminder reminder = reminderService.findReminderById(UUID.fromString(reminderId));
            reminderService.deleteReminder(reminder.getReminderId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ReminderNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IncorrectRequestException(String.format("%s is not a valid UUID.", reminderId));
        }
    }
}
