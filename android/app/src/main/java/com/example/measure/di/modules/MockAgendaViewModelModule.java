package com.example.measure.di.modules;

import com.example.measure.features.agenda.AgendaViewModel;
import com.example.measure.features.agenda.MockAgendaViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake agenda view model.
 */
@Module
public class MockAgendaViewModelModule {
    /**
     * Return the agenda view model factory to be provided for all dependents.
     *
     * @param mockAvmFactory fake agenda view model factory
     * @return agenda view model factory to be provided
     */
    @Provides
    public AgendaViewModel.Factory provideAgendaViewModelFactory(
            MockAgendaViewModel.Factory mockAvmFactory) {
        return mockAvmFactory;
    }

    /**
     * Return the mock agenda view model factory to be provided for all dependents.
     *
     * @return mock agenda view model factory to be provided
     */
    @Provides
    public MockAgendaViewModel.Factory provideMockAgendaViewModelFactory() {
        return new MockAgendaViewModel.Factory();
    }
}
