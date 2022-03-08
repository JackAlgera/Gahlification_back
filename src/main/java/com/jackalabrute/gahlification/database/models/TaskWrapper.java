package com.jackalabrute.gahlification.database.models;

import com.jackalabrute.gahlification.database.models.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskWrapper extends Task implements Serializable {
    private List<Tag> tags;
    private List<TaskStep> taskSteps;

    public TaskWrapper(Task task, List<Tag> tags, List<TaskStep> taskSteps) {
        super(
                task.getTaskId(),
                task.getTaskName(),
                task.getDescription(),
                task.getDoTaskAtDate(),
                task.getRepeatDelay(),
                task.getCreatedOn(),
                task.getLastModified()
        );
        this.tags = tags;
        this.taskSteps = taskSteps;
    }
}
