package com.example.measure.di.modules;

import com.example.measure.models.login.LocalLoginRepository;
import com.example.measure.models.login.LoginRepository;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the login repository.
 */
@Module
public class LoginRepositoryModule {
    /**
     * Return the login repository to be provided for all dependents.
     *
     * @param localLoginRepo login repository using a local database
     * @return login repository to be used for login session data access
     */
    @Provides
    public LoginRepository provideLoginRepository(
            LocalLoginRepository localLoginRepo) {
        return localLoginRepo;
    }
}
