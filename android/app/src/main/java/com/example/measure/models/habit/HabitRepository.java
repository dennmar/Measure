package com.example.measure.models.habit;

import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Habit;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;
import com.example.measure.utils.InvalidQueryException;

import java.util.List;

/**
 * A repository for accessing habit data.
 */
public interface HabitRepository {
    /**
     * Retrieve all the habits for the user from the database.
     *
     * @param user user to retrieve habits for
     * @return all the habits belonging to the user
     * @throws DBOperationException if the habits could not be fetched
     */
    LiveData<List<Habit>> getHabits(User user) throws DBOperationException;

    /**
     * Store a habit for the user in the database.
     *
     * @param user  user who will own the habit
     * @param habit habit to store for the user
     * @throws DBOperationException  if the habit could not be added
     * @throws InvalidQueryException if the habit to add does not belong to
     *                               the user
     */
    void addHabit(User user, Habit habit)
            throws DBOperationException, InvalidQueryException;

    /**
     * Store a habit completion in the database.
     *
     * @param user            user who owns the habit
     * @param habit           habit that was completed
     * @param habitCompletion completion info for the habit
     * @throws DBOperationException  if the habit completion could not be
     *                               stored
     * @throws InvalidQueryException if the habit to add the completion to
     *                               does not belong to the user
     */
    void addHabitCompletion(User user, Habit habit,
            HabitCompletion habitCompletion)
            throws DBOperationException, InvalidQueryException;
}
