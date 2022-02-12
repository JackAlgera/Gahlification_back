package com.jackalabrute.gahlification.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Data
public class Task {
    private UUID taskId;
    private String taskName;
    private String description;
    private Instant doTaskAtDate;
    private Long repeatDelay; // In seconds
}
