package com.example.measure.di.components;

import com.example.measure.di.Scopes;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.test.MockAgendaViewModelModule;
import com.example.measure.di.modules.test.MockRegisterViewModelModule;
import com.example.measure.di.modules.test.MockUserRepositoryModule;

import dagger.Component;

/**
 * A component targeted for agenda fragment tests that makes Dagger create a
 * graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        MockAgendaViewModelModule.class,
        // Below are modules that are needed for the entire Measure application
        // to work.
        MockRegisterViewModelModule.class,
        MockUserRepositoryModule.class
})
public interface TestAgendaFragmentComponent extends ApplicationComponent {}
