package com.jackalabrute.WebServer.models;

import java.time.Instant;
import java.util.UUID;

public class Task {
    private String taskName;
    private UUID taskId;
    private Instant doTaskAtDate;
    private Timestamp repeatDelay;

    public Task(String taskName, Instant doTaskAtDate, Timestamp repeatDelay, UUID taskId) {
        this.taskName = taskName;
        this.taskId = taskId;
        this.doTaskAtDate = doTaskAtDate;
        this.repeatDelay = repeatDelay;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public Instant getDoTaskAtDate() {
        return doTaskAtDate;
    }

    public void setDoTaskAtDate(Instant doTaskAtDate) {
        this.doTaskAtDate = doTaskAtDate;
    }

    public Timestamp getRepeatDelay() {
        return repeatDelay;
    }

    public void setRepeatDelay(Timestamp repeatDelay) {
        this.repeatDelay = repeatDelay;
    }
}
