package com.example.measure.models.data;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

/**
 * A task for a user to be completed on a due date.
 */
public class Task {
    private long id;
    private long userId;
    private String name;
    private Duration timeWorked;
    private LocalDate localDueDate;
    private boolean isCompleted;

    /**
     * Initialize member variables to defaults.
     */
    public Task() {
        id = 0;
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
    public Task(long id, long userId, String name, Duration timeWorked,
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
        return "id: " + id
                + ", userId: " + userId
                + ", name: " + name
                + ", timeWorked: " + timeWorked
                + ", localDueDate: " + localDueDate
                + ", isCompleted: " + isCompleted;
    }

    /**
     * Return the string representation of the due date.
     *
     * @return the string representing the due date (ex: "January 17, 2001")
     */
    public String getLocalDueDateString() {
        return localDueDate.toString("MMMM d, YYYY");
    }

    /* Getters and setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getTimeWorked() {
        return timeWorked;
    }

    public void setTimeWorked(Duration timeWorked) {
        this.timeWorked = timeWorked;
    }

    public LocalDate getLocalDueDate() {
        return localDueDate;
    }

    public void setLocalDueDate(LocalDate localDueDate) {
        this.localDueDate = localDueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
