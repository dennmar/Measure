package com.example.measure.di;

import com.example.measure.LocalLoginRepository;
import com.example.measure.LoginRepository;

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
     * @return login repository to be used for login session data access
     */
    @Provides
    public LoginRepository provideLoginRepository() {
        return new LocalLoginRepository();
    }
}
