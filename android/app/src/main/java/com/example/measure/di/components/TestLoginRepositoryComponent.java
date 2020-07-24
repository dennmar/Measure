package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.prod.LoginRepositoryModule;
import com.example.measure.di.modules.test.MockLoginDaoModule;
import com.example.measure.di.modules.test.MockUserDaoModule;
import com.example.measure.models.login.LoginRepository;

import dagger.Component;

/**
 * A component targeted for login repository tests that makes Dagger create
 * a graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        LoginRepositoryModule.class,
        MockLoginDaoModule.class,
        MockUserDaoModule.class
})
public interface TestLoginRepositoryComponent {
    LoginRepository loginRepository();
}
