package com.jackalabrute.WebServer.models;

import java.time.Instant;

public class Task {
    private Instant currentDate;
    private Time repeatDelay;

    public Task(Instant currentDate, Time repeatDelay) {
        this.currentDate = currentDate;
        this.repeatDelay = repeatDelay;
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
