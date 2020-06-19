package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.MockLoginRepositoryModule;
import com.example.measure.di.modules.MockTaskRepositoryModule;
import com.example.measure.features.agenda.AgendaViewModel;

import dagger.Component;

/**
 * A component targeted for agenda view model tests that makes Dagger create
 * a graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        MockTaskRepositoryModule.class,
        MockLoginRepositoryModule.class
})
public interface TestAgendaViewModelComponent {
    AgendaViewModel.Factory avmFactory();
}
