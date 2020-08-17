package com.example.measure.di.modules.test;

import com.example.measure.models.habit.HabitDao;
import com.example.measure.models.habit.MockHabitDao;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake habit DAO.
 */
@Module
public class MockHabitDaoModule {
    /**
     * Return the habit DAO to be provided for all dependents.
     *
     * @param mockHabitDao fake habit DAO
     * @return habit DAO to be used for habit database access
     */
    @Provides
    public HabitDao provideHabitDao(MockHabitDao mockHabitDao) {
        return mockHabitDao;
    }
}

