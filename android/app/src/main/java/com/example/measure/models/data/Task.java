package com.example.measure.models.data;

import java.time.Duration;
import java.util.Date;

/**
 * A task for a user to be completed on a due date.
 */
public class Task {
    public int id;
    public int userId;
    public String name;
    public Duration timeWorked;
    public Date localDueDate;
    public boolean isCompleted;

    /**
     * Initialize member variables to defaults.
     */
    public Task() {
        id = -1;
        userId = -1;
        name = null;
        timeWorked = null;
        localDueDate = null;
        isCompleted = false;
    }

    /**
     * Initialize member variables.
     *
     * @param id           id of the task stored in the database
     * @param userId       database id of the owner
     * @param name         task name
     * @param timeWorked   amount of time worked on the task
     * @param localDueDate due date in local time
     * @param isCompleted  whether the task has been completed
     */
    public Task(int id, int userId, String name, Duration timeWorked,
            Date localDueDate, boolean isCompleted) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.timeWorked = timeWorked;
        this.localDueDate = localDueDate;
        this.isCompleted = isCompleted;
    }
}
