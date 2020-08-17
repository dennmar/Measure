package com.example.measure.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.measure.models.data.Habit;

import java.util.HashSet;

@Entity(tableName = "habits")
public class RoomHabit {
    // Should be equal to 0 to allow id to be auto-generated.
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name="user_id")
    @NonNull
    private long userId;

    private String name;

    /**
     * Initialize member variables to default values.
     */
    public RoomHabit() {
        id = 0;
        userId = -1;
        name = null;
    }

    /**
     * Initialize member variables from a Habit.
     */
    public RoomHabit(Habit habit) {
        id = habit.getId();
        userId = habit.getUserId();
        name = habit.getName();
    }

    /**
     * Return the Habit representation of this RoomHabit.
     *
     * @return the RoomHabit converted to a Habit
     */
    public Habit toHabit() {
        // TODO: must define relation to get completions
        return new Habit(id, userId, name, new HashSet<>());
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
}
