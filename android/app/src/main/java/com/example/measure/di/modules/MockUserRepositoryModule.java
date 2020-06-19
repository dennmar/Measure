package com.example.measure.di.modules;

import com.example.measure.models.user.LocalUserRepository;
import com.example.measure.models.user.MockUserRepository;
import com.example.measure.models.user.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake user repository.
 */
@Module
public class MockUserRepositoryModule {
    /**
     * Return the user repository to be provided for all dependents.
     *
     * @return user repository to be used for user data access
     */
    @Provides
    public UserRepository provideUserRepository() {
        return new MockUserRepository();
    }
}
