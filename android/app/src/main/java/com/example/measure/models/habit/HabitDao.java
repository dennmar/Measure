package com.example.measure.models.habit;

import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Habit;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.List;

/**
 * A habit database access object.
 */
public interface HabitDao {
    /**
     * Retrieve all habits for the user from the database.
     *
     * @param user user to fetch habits for
     * @return observable list of all the user's habits
     * @throws DBOperationException if the user's habits could not be fetched
     */
    LiveData<List<Habit>> getHabits(User user) throws DBOperationException;

    /**
     * Store a habit for the user in the database.
     *
     * @param user  user who will own the habit
     * @param habit habit to store for the user
     * @throws DBOperationException if the habit could not be stored
     */
    void addHabit(User user, Habit habit) throws DBOperationException;

    /**
     * Store a habit completion in the database.
     *
     * @param habitCompletion completion info for the habit
     * @throws DBOperationException if the habit completion could not be stored
     */
    void addHabitCompletion(HabitCompletion habitCompletion)
            throws DBOperationException;
}
