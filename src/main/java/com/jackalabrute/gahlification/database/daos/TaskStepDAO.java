package com.jackalabrute.gahlification.database.daos;

import com.jackalabrute.gahlification.database.models.TaskStep;

import java.util.List;
import java.util.UUID;

public interface TaskStepDAO extends DefaultDAO<TaskStep> {
    List<TaskStep> findAllTaskStepsByTaskId(UUID taskId);
}
