package com.example.measure.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.measure.models.data.Task;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

/**
 * A task for the Room database.
 */
@Entity(tableName = "tasks")
@TypeConverters({RoomTaskConverters.class})
public class RoomTask {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="user_id")
    @NonNull
    public int userId;

    public String name;

    @ColumnInfo(name="time_worked")
    public Duration timeWorked;

    @ColumnInfo(name="local_due_date")
    public LocalDate localDueDate;

    @ColumnInfo(name="is_completed")
    public boolean isCompleted;

    /**
     * Initialize member variables to default values.
     */
    public RoomTask() {
        userId = -1;
        name = null;
        timeWorked = null;
        localDueDate = null;
        isCompleted = false;
    }

    /**
     * Initialize member variables from a Task.
     *
     * @param task task holding data to initialize the Room task
     */
    public RoomTask(Task task) {
        this.id = task.getId();
        this.userId = task.getUserId();
        this.name = task.getName();
        this.timeWorked = task.getTimeWorked();
        this.localDueDate = task.getLocalDueDate();
        this.isCompleted = task.isCompleted();
    }

    /**
     * Return the Task representation of this RoomTask.
     *
     * @return the RoomTask converted to a Task
     */
    public Task toTask() {
        return new Task(this.id, this.userId, this.name, this.timeWorked,
                this.localDueDate, this.isCompleted);
    }
}
