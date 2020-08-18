package com.example.measure.models.habit;

import com.example.measure.models.data.HabitCompletion;
import com.example.measure.utils.DBOperationException;

/**
 * A habit completion database access object.
 */
public interface HabitCompletionDao {
    /**
     * Store a habit completion in the database.
     *
     * @param habitCompletion completion info for the habit
     * @throws DBOperationException if the habit completion could not be stored
     */
    void addHabitCompletion(HabitCompletion habitCompletion)
            throws DBOperationException;
}
