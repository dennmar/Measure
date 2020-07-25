package com.example.measure.di.modules.prod;

import com.example.measure.features.login.DaggerLoginViewModel;
import com.example.measure.features.login.LoginViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the login view model.
 */
@Module
public class LoginViewModelModule {
    /**
     * Return the login view model factory to be provided to all dependents.
     *
     * @param daggerLvmFactory login view model factory using dagger injection
     * @return login view model factory to create the login view model
     */
    @Provides
    public LoginViewModel.Factory provideLoginViewModel(
            DaggerLoginViewModel.Factory daggerLvmFactory) {
        return daggerLvmFactory;
    }
}
