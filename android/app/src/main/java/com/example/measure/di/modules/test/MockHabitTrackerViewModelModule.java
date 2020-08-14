package com.example.measure.di.modules.test;

import com.example.measure.features.habit_tracker.HabitTrackerViewModel;
import com.example.measure.features.habit_tracker.MockHabitTrackerViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake habit tracker view
 * model.
 */
@Module
public class MockHabitTrackerViewModelModule {
    /**
     * Return the habit tracker view model factory to be provided for all
     * dependents.
     *
     * @param mhtvmFactory fake habit tracker view model factory
     * @return habit tracker view model factory to create the habit tracker
     * view model
     */
    @Provides
    public HabitTrackerViewModel.Factory provideHabitTrackerViewModelFactory(
            MockHabitTrackerViewModel.Factory mhtvmFactory) {
        return mhtvmFactory;
    }

    /**
     * Return the mock habit tracker view model factory to be provided for all
     * dependents.
     *
     * @return mock habit tracker view model factory to be provided
     */
    @Provides
    public MockHabitTrackerViewModel.Factory provideMockHTVMFactory() {
        return new MockHabitTrackerViewModel.Factory();
    }
}
