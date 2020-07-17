package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.prod.UserRepositoryModule;
import com.example.measure.di.modules.test.MockUserDaoModule;
import com.example.measure.models.user.UserRepository;

import dagger.Component;

/**
 * A component targeted for user repository tests that makes Dagger create a
 * graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        UserRepositoryModule.class,
        MockUserDaoModule.class
})
public interface TestUserRepositoryComponent {
    UserRepository userRepository();
}
