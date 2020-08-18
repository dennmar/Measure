package com.example.measure.di.modules.test;

import com.example.measure.models.habit.HabitCompletionDao;
import com.example.measure.models.habit.MockHabitCompletionDao;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake habit completion DAO.
 */
@Module
public class MockHabitCompletionDaoModule {
    /**
     * Return the habit completion DAO to be provided for all dependents.
     *
     * @param mockHabitCompletionDao fake habit completion DAO
     * @return habit completion DAO to be used for habit completion
     * database access
     */
    @Provides
    public HabitCompletionDao provideHabitCompletionDao(
            MockHabitCompletionDao mockHabitCompletionDao) {
        return mockHabitCompletionDao;
    }
}
