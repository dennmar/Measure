package com.example.measure.models.habit;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.utils.DBOperationException;

import javax.inject.Inject;

/**
 * A SQLite habit completion database access object.
 */
public class SQLiteHabitCompletionDao implements HabitCompletionDao {
    /**
     * Initialize member variables.
     *
     * @param db SQLite database where habit completions are stored
     */
    @Inject
    public SQLiteHabitCompletionDao(MeasureRoomDatabase db) {

    }

    /**
     * Store a habit completion in the database.
     *
     * @param habitCompletion completion info for the habit
     * @throws DBOperationException if the habit completion could not be stored
     */
    @Override
    public void addHabitCompletion(HabitCompletion habitCompletion)
            throws DBOperationException {

    }
}
