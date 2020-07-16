package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.test.MockAgendaViewModelModule;
import com.example.measure.di.modules.test.MockRegisterViewModelModule;

import dagger.Component;

/**
 * A component targeted for register fragment tests that makes Dagger create a
 * graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        MockRegisterViewModelModule.class,
        // Below are modules that are needed for the entire Measure application
        // to work.
        MockAgendaViewModelModule.class
})
public interface TestRegisterFragmentComponent extends ApplicationComponent {

}