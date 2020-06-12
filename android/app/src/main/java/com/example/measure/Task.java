package com.example.measure;

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
