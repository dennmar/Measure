package com.example.measure.di.modules.test;

import com.example.measure.features.register.MockRegisterViewModel;
import com.example.measure.features.register.RegisterViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake register view model.
 */
@Module
public class MockRegisterViewModelModule {
    /**
     * Return the register view model factory to be provided to all dependents.
     *
     * @param mockRvmFactory fake register view model factory
     * @return register view model factory to create the register view model
     */
    @Provides
    public RegisterViewModel.Factory provideRegisterViewModelFactory(
            MockRegisterViewModel.Factory mockRvmFactory) {
        return mockRvmFactory;
    }

    /**
     * Return the mock register view model factory to be provided for all
     * dependents.
     *
     * @return mock register view model factory to be provided
     */
    @Provides
    public MockRegisterViewModel.Factory
            provideMockRegisterViewModelFactory() {
        return new MockRegisterViewModel.Factory();
    }
}
