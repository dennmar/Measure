package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.prod.HabitRepositoryModule;
import com.example.measure.di.modules.test.MockHabitDaoModule;
import com.example.measure.models.habit.HabitRepository;

import dagger.Component;

@Component(modules = {
        AssistedInjectModule.class,
        HabitRepositoryModule.class,
        MockHabitDaoModule.class
})
public interface TestHabitRepositoryComponent {
    HabitRepository habitRepository();
}
