package com.jackalabrute.WebServer.models;

import java.time.Instant;
import java.util.UUID;

public class Task {
    private String taskName;
    private UUID taskId;
    private Instant currentDate;
    private Time repeatDelay;

    public Task(String taskName, Instant currentDate, Time repeatDelay, UUID taskId) {
        this.taskName = taskName;
        this.taskId = taskId;
        this.currentDate = currentDate;
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

    public Instant getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Instant currentDate) {
        this.currentDate = currentDate;
    }

    public Time getRepeatDelay() {
        return repeatDelay;
    }

    public void setRepeatDelay(Time repeatDelay) {
        this.repeatDelay = repeatDelay;
    }
}
