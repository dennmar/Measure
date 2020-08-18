package com.example.measure.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.joda.time.LocalDate;

/**
 * An instance when a habit was completed for the Room database.
 */
@Entity(tableName = "habit_completions")
@TypeConverters({RoomConverters.class})
public class RoomHabitCompletion {
    // Should be equal to 0 to allow id to be auto-generated.
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "habit_id")
    @NonNull
    private long habitId;

    @ColumnInfo(name = "local_completed_date")
    @NonNull
    private LocalDate localCompletedDate;

    /**
     * Initialize member variables to default values.
     */
    public RoomHabitCompletion() {
        id = 0;
        habitId = -1;
        localCompletedDate = null;
    }

    /**
     * Initialize member variables.
     */
    public RoomHabitCompletion(long habitId, LocalDate localCompletedDate) {
        id = 0;
        this.habitId = habitId;
        this.localCompletedDate = localCompletedDate;
    }

    /* Getters and setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHabitId() {
        return habitId;
    }

    public void setHabitId(long habitId) {
        this.habitId = habitId;
    }

    @NonNull
    public LocalDate getLocalCompletedDate() {
        return localCompletedDate;
    }

    public void setLocalCompletedDate(@NonNull LocalDate localCompletedDate) {
        this.localCompletedDate = localCompletedDate;
    }
}
