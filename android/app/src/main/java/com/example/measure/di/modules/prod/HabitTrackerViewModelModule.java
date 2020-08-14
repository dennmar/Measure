package com.example.measure.di.modules.prod;

import com.example.measure.features.habit_tracker.DaggerHabitTrackerViewModel;
import com.example.measure.features.habit_tracker.HabitTrackerViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the habit tracker view model.
 */
@Module
public class HabitTrackerViewModelModule {
    /**
     * Return the habit tracker view model factory to be provided for all
     * dependents.
     *
     * @param dhtvmFactory habit tracker view model factory using dagger
     *                     injection
     * @return habit tracker view model factory to create the habit tracker
     * view model
     */
    @Provides
    public HabitTrackerViewModel.Factory provideHabitTrackerViewModelFactory(
            DaggerHabitTrackerViewModel.Factory dhtvmFactory) {
        return dhtvmFactory;
    }
}
