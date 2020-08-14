package com.example.measure.di.modules.prod;

import com.example.measure.models.habit.HabitRepository;
import com.example.measure.models.habit.LocalHabitRepository;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the habit repository.
 */
@Module
public class HabitRepositoryModule {
    /**
     * Return the habit repository to be provided for all dependents.
     *
     * @param localHabitRepo habit repository using local database
     * @return habit repository to be used for habit data access
     */
    @Provides
    public HabitRepository provideHabitRepository(
            LocalHabitRepository localHabitRepo) {
        return localHabitRepo;
    }
}
