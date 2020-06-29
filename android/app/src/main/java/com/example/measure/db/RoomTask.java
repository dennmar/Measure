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
        this.userId = task.userId;
        this.name = task.name;
        this.timeWorked = task.timeWorked;
        this.localDueDate = task.localDueDate;
        this.isCompleted = task.isCompleted;
    }
}
