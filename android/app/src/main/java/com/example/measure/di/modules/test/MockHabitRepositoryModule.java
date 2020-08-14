package com.example.measure.di.modules.test;

import com.example.measure.models.habit.HabitRepository;
import com.example.measure.models.habit.LocalHabitRepository;
import com.example.measure.models.habit.MockHabitRepository;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake habit repository.
 */
@Module
public class MockHabitRepositoryModule {
    /**
     * Return the habit repository to be provided for all dependents.
     *
     * @param mockHabitRepo fake habit repository
     * @return habit repository to be used for habit data access
     */
    @Provides
    public HabitRepository provideHabitRepository(
            MockHabitRepository mockHabitRepo) {
        return mockHabitRepo;
    }
}

