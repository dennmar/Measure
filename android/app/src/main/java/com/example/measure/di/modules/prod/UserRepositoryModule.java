package com.example.measure.di.modules.prod;

import com.example.measure.di.Scopes;
import com.example.measure.models.user.LocalUserRepository;
import com.example.measure.models.user.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the user repository.
 */
@Module
public class UserRepositoryModule {
    /**
     * Return the user repository to be provided for all dependents.
     *
     * @param localUserRepo user repository using a local database
     * @return user repository to be used for user data access
     */
    @Provides
    @Scopes.ApplicationScope
    public UserRepository provideUserRepository(
            LocalUserRepository localUserRepo) {
        return localUserRepo;
    }
}
