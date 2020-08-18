package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.prod.HabitRepositoryModule;
import com.example.measure.di.modules.test.MockHabitCompletionDaoModule;
import com.example.measure.di.modules.test.MockHabitDaoModule;
import com.example.measure.models.habit.HabitRepository;

import dagger.Component;

/**
 * A component targeted for habit repository tests that makes Dagger create
 * a graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        HabitRepositoryModule.class,
        MockHabitDaoModule.class,
        MockHabitCompletionDaoModule.class
})
public interface TestHabitRepositoryComponent {
    HabitRepository habitRepository();
}
