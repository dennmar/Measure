package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.MockLoginRepositoryModule;
import com.example.measure.di.modules.MockTaskDaoModule;
import com.example.measure.di.modules.MockTaskRepositoryModule;
import com.example.measure.di.modules.MockUserRepositoryModule;
import com.example.measure.features.agenda.DaggerAgendaViewModel;

import dagger.Component;

/**
 * A component targeted for agenda view model tests that makes Dagger create
 * a graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        MockTaskRepositoryModule.class,
        MockTaskDaoModule.class,
        MockLoginRepositoryModule.class,
        MockUserRepositoryModule.class
})
public interface TestAgendaViewModelComponent {
    DaggerAgendaViewModel.Factory avmFactory();
}
