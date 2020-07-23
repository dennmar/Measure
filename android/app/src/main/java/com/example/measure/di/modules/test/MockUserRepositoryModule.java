package com.example.measure.di.modules.test;

import com.example.measure.di.Scopes;
import com.example.measure.models.user.MockUserRepository;
import com.example.measure.models.user.UserRepository;

import javax.inject.Singleton;

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
     * @param mockUserRepo fake user repository
     * @return user repository to be used for user data access
     */
    @Provides
    @Scopes.ApplicationScope
    public UserRepository provideUserRepository(
            MockUserRepository mockUserRepo) {
        return mockUserRepo;
    }
}
