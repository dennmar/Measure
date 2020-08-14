package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.prod.HabitTrackerViewModelModule;
import com.example.measure.di.modules.test.MockHabitRepositoryModule;
import com.example.measure.di.modules.test.MockLoginRepositoryModule;
import com.example.measure.features.habit_tracker.HabitTrackerViewModel;

import dagger.Component;

/**
 * A component targeted for habit tracker view model tests that makes Dagger
 * create a graph of dependencies and exposes objects from the application
 * graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        HabitTrackerViewModelModule.class,
        MockLoginRepositoryModule.class,
        MockHabitRepositoryModule.class
})
public interface TestHabitTrackerViewModelComponent {
    HabitTrackerViewModel.Factory htvmFactory();
}
