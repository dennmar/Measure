package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.prod.RegisterViewModelModule;
import com.example.measure.di.modules.test.MockUserRepositoryModule;
import com.example.measure.features.register.RegisterViewModel;

import dagger.Component;

/**
 * A component targeted for register view model tests that makes Dagger create
 * a graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        RegisterViewModelModule.class,
        MockUserRepositoryModule.class
})
public interface TestRegisterViewModelComponent {
    RegisterViewModel.Factory rvmFactory();
}
