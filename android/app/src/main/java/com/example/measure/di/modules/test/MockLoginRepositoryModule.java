package com.example.measure.di.modules.test;

import com.example.measure.models.login.LoginRepository;
import com.example.measure.models.login.MockLoginRepository;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake login repository.
 */
@Module
public class MockLoginRepositoryModule {
    /**
     * Return the login repository to be provided for all dependents.
     *
     * @return login repository to be used for login session data access
     */
    @Provides
    public LoginRepository provideLoginRepository() {
        return new MockLoginRepository();
    }
}
