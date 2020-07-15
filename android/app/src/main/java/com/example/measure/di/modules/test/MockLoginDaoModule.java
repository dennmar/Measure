package com.example.measure.di.modules.test;

import com.example.measure.models.login.LoginDao;
import com.example.measure.models.login.MockLoginDao;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a mock login DAO.
 */
@Module
public class MockLoginDaoModule {
    /**
     * Return the login DAO to be provided for all dependents.
     *
     * @param mockLoginDao fake login DAO
     * @return login DAO to be used for login session data access
     */
    @Provides
    public LoginDao provideLoginDao(MockLoginDao mockLoginDao) {
        return mockLoginDao;
    }
}
