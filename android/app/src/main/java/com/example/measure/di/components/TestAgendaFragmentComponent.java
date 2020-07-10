package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.MockAgendaViewModelModule;
import com.example.measure.features.agenda.AgendaFragment;
import com.example.measure.features.agenda.MockAgendaViewModel;

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
