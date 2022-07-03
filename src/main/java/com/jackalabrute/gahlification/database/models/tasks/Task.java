package com.jackalabrute.gahlification.database.models.tasks;

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
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    private UUID taskId;
    private String taskName;
    private String description;
    private Instant doTaskAtDate;
    private Long repeatDelay; // In seconds
    private Instant createdOn;
    private Instant lastModified;

    public void updateValues(Task task) {
        this.taskName = task.getTaskName();
        this.description = task.getDescription();
        this.doTaskAtDate = task.getDoTaskAtDate();
        this.repeatDelay = task.getRepeatDelay();
        this.lastModified = Instant.now();
    }
}
