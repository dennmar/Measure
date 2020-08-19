package com.example.measure.models.habit;

import com.example.measure.models.data.HabitCompletion;
import com.example.measure.utils.DBOperationException;

import javax.inject.Inject;

/**
 * A fake habit completion database access object.
 */
public class MockHabitCompletionDao implements HabitCompletionDao {
    @Inject
    public MockHabitCompletionDao() {}

    /**
     * Store a habit completion in the database.
     *
     * @param habitCompletion completion info for the habit
     */
    @Override
    public void addHabitCompletion(HabitCompletion habitCompletion)
            throws DBOperationException {
        MockHabitDao.addHabitCompletion(MockHabitDao.lastUser, habitCompletion);
    }
}
