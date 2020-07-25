package com.example.measure.di.modules.test;

import com.example.measure.features.login.LoginViewModel;
import com.example.measure.features.login.MockLoginViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake login view model.
 */
@Module
public class MockLoginViewModelModule {
    /**
     * Return the login view model factory to be provided to all dependents.
     *
     * @param mockLvmFactory fake login view model factory
     * @return login view model factory to create the login view model
     */
    @Provides
    public LoginViewModel.Factory provideLoginViewModel(
            MockLoginViewModel.Factory mockLvmFactory) {
        return mockLvmFactory;
    }

    /**
     * Return the mock login view model factory to be provided for all
     * dependents.
     *
     * @return mock register view model factory to be provided
     */
    @Provides
    public MockLoginViewModel.Factory provideMockLoginViewModelFactory() {
        return new MockLoginViewModel.Factory();
    }
}
