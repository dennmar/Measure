package com.example.measure.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.measure.models.data.Habit;

import java.util.List;

/**
 * Habit database access object interacting with the Room database.
 */
@Dao
public interface RoomHabitDao {
    /**
     * Find all habits that belong to a user.
     *
     * @param userId id of the user to find habits for
     * @return observable list of habits from the Room database
     */
    // TODO: fix * to specify columns
    @Query("SELECT * FROM habits WHERE user_id == :userId")
    LiveData<List<RoomHabit>> getHabits(long userId);

    /**
     * Insert a habit in the Room database.
     *
     * @param habit habit to insert
     * @return row id for the inserted item
     */
    @Insert
    long insert(RoomHabit habit);
}
