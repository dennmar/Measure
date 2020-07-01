package com.example.measure.models.data;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

/**
 * A task for a user to be completed on a due date.
 */
public class Task {
    public int id;
    public int userId;
    public String name;
    public Duration timeWorked;
    public LocalDate localDueDate;
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
     * @param localDueDate local due date without a time zone
     * @param isCompleted  whether the task has been completed
     */
    public Task(int id, int userId, String name, Duration timeWorked,
            LocalDate localDueDate, boolean isCompleted) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.localDueDate = localDueDate;
        this.isCompleted = isCompleted;

        if (timeWorked != null && timeWorked.getStandardSeconds() < 0) {
            this.timeWorked = null;
        }
        else {
            this.timeWorked = timeWorked;
        }
    }

    @Override
    public String toString() {
        return "id: " + this.id
                + ", userId: " + this.userId
                + ", name: " + this.name
                + ", timeWorked: " + this.timeWorked
                + ", localDueDate: " + this.localDueDate
                + ", isCompleted: " + this.isCompleted;
    }
}
