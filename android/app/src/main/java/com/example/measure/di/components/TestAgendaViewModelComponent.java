package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.test.MockLoginRepositoryModule;
import com.example.measure.di.modules.test.MockTaskDaoModule;
import com.example.measure.di.modules.test.MockTaskRepositoryModule;
import com.example.measure.di.modules.test.MockUserRepositoryModule;
import com.example.measure.features.agenda.viewmodel.DaggerAgendaViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

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
@Singleton
public interface TestAgendaViewModelComponent {
    DaggerAgendaViewModel.Factory avmFactory();
}
