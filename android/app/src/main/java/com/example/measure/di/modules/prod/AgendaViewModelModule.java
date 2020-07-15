package com.example.measure.di.modules.prod;

import com.example.measure.features.agenda.viewmodel.AgendaViewModel;
import com.example.measure.features.agenda.viewmodel.DaggerAgendaViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the agenda view model.
 */
@Module
public class AgendaViewModelModule {
    /**
     * Return the agenda view model factory to be provided for all dependents.
     *
     * @param daggerAvmFactory agenda view model factory using dagger injection
     * @return agenda view model factory to create the agenda view model
     */
    @Provides
    public AgendaViewModel.Factory provideAgendaViewModelFactory(
            DaggerAgendaViewModel.Factory daggerAvmFactory) {
        return daggerAvmFactory;
    }
}
