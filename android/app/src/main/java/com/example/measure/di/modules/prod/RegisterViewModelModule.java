package com.example.measure.di.modules.prod;

import com.example.measure.features.register.DaggerRegisterViewModel;
import com.example.measure.features.register.RegisterViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the register view model.
 */
@Module
public class RegisterViewModelModule {
    /**
     * Return the register view model factory to be provided to all dependents.
     *
     * @param daggerRvmFactory register view model factory using dagger
     *                         injection
     * @return register view model factory to create the register view model
     */
    @Provides
    public RegisterViewModel.Factory provideRegisterViewModelFactory(
            DaggerRegisterViewModel.Factory daggerRvmFactory) {
        return daggerRvmFactory;
    }
}
