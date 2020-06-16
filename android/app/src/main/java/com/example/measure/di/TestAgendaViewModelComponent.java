package com.example.measure.di;

import com.example.measure.AgendaViewModel;

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
