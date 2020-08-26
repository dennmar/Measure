package com.example.measure.db;

import androidx.room.Dao;
import androidx.room.Insert;

/**
 * Habit completion database access object interacting with the Room database.
 */
@Dao
public interface RoomHabitCompletionDao {
    /**
     * Insert a habit completion in the Room database.
     *
     * @param habitCompletion info about the completion of a habit
     * @return row id for the inserted item
     */
    @Insert
    long insert(RoomHabitCompletion habitCompletion);
}
