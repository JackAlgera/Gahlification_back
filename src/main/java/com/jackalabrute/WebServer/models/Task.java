package com.jackalabrute.WebServer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Data
public class Task {
    private String taskName;
    private UUID taskId;
    private Instant doTaskAtDate;
    private Long repeatDelay; // In seconds
}
