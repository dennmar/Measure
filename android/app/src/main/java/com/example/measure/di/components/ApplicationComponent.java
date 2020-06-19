package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.LoginRepositoryModule;
import com.example.measure.di.modules.TaskRepositoryModule;
import com.example.measure.features.agenda.AgendaViewModel;

import dagger.Component;

/**
 * A component that makes Dagger create a graph of dependencies and exposes
 * objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        TaskRepositoryModule.class,
        LoginRepositoryModule.class
})
public interface ApplicationComponent {
    AgendaViewModel.Factory avmFactory();
}
