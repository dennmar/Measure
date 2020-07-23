package com.example.measure.di.components;

import com.example.measure.di.Scopes;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.prod.AgendaViewModelModule;
import com.example.measure.di.modules.test.MockLoginRepositoryModule;
import com.example.measure.di.modules.test.MockTaskDaoModule;
import com.example.measure.di.modules.test.MockTaskRepositoryModule;
import com.example.measure.di.modules.test.MockUserRepositoryModule;
import com.example.measure.features.agenda.viewmodel.AgendaViewModel;
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
        AgendaViewModelModule.class,
        MockTaskRepositoryModule.class,
        MockTaskDaoModule.class,
        MockLoginRepositoryModule.class,
        MockUserRepositoryModule.class
})
@Scopes.ApplicationScope
public interface TestAgendaViewModelComponent {
    AgendaViewModel.Factory avmFactory();
}
