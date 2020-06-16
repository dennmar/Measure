package com.example.measure.di;

import com.example.measure.LocalLoginRepository;
import com.example.measure.LoginRepository;
import com.example.measure.MockLoginRepository;

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
