package com.example.measure.di;

import com.example.measure.AgendaViewModel;

import dagger.Component;

/**
 * A component that makes Dagger create a graph of dependencies and exposes
 * objects from the application graph.
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    AgendaViewModel.Factory avmFactory();
}
