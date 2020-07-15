package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.test.MockAgendaViewModelModule;

import dagger.Component;

/**
 * A component targeted for agenda view model tests that makes Dagger create a
 * graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        MockAgendaViewModelModule.class
})
public interface TestAgendaFragmentComponent extends ApplicationComponent {

}
