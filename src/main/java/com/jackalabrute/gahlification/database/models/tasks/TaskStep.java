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
@Table(name = "task_steps")
public class TaskStep implements Serializable {
    @Id
    private UUID taskStepId;
    private UUID taskId;
    private String description;
    private Instant createdOn;
    private Instant lastModified;

    public void updateValues(TaskStep step) {
        this.taskId = step.getTaskId();
        this.description = step.getDescription();
        this.lastModified = Instant.now();
    }
}
