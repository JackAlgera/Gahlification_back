package com.jackalabrute.homeclusterbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reminders")
public class Reminder implements Serializable {
    @Id
    private UUID reminderId;
    private String name;
    private String description;
    private Instant dueDate;
    private Instant startNotifyingDate;
    private Long pingFrequencyInterval; // In seconds
    private Instant createdOn;
    private Instant lastModified;

    public void updateValues(Reminder reminder) {
        this.name = reminder.getName();
        this.description = reminder.getDescription();
        this.dueDate = reminder.getDueDate();
        this.startNotifyingDate = reminder.getStartNotifyingDate();
        this.pingFrequencyInterval = reminder.getPingFrequencyInterval();
        this.lastModified = Instant.now();
    }
}
