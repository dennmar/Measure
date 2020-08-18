package com.example.measure.features.habit_tracker;

import android.os.Bundle;

import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Habit;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.utils.DBOperationException;

import java.util.List;

/**
 * A view model that handles the interaction between the habit tracker view
 * (fragment) and the model.
 */
public interface HabitTrackerViewModel {
    /**
     * Factory for creating a habit tracker view model.
     */
    interface Factory {
        /**
         * Create an instance of a habit tracker model with the given saved
         * instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created habit tracker view model
         */
        HabitTrackerViewModel create(Bundle savedInstanceState);
    }

    /**
     * Retrieve all habits for the user.
     *
     * @return observable list of habits belonging to the user
     * @throws DBOperationException if the habits could not be fetched
     */
    LiveData<List<Habit>> getHabits() throws DBOperationException;

    /**
     * Add a habit for the user.
     *
     * @param habit habit to be added
     * @throws DBOperationException if the habit could not be added
     */
    void addHabit(Habit habit) throws DBOperationException;

    /**
     * Add a habit completion.
     *
     * @param habit           habit that was completed
     * @param habitCompletion completion info for the habit
     * @throws DBOperationException if the habit completion could not be added
     */
    void addHabitCompletion(Habit habit, HabitCompletion habitCompletion)
            throws DBOperationException;
}
