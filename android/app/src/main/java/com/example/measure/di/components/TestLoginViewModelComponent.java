package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.prod.LoginViewModelModule;
import com.example.measure.di.modules.test.MockLoginRepositoryModule;
import com.example.measure.features.login.LoginViewModel;

import dagger.Component;

/**
 * A component targeted for login view model tests that makes Dagger create
 * a graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        LoginViewModelModule.class,
        MockLoginRepositoryModule.class
})
public interface TestLoginViewModelComponent {
    LoginViewModel.Factory lvmFactory();
}
