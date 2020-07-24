package com.example.measure.di.modules.test;

import com.example.measure.di.Scopes;
import com.example.measure.models.user.MockUserDao;
import com.example.measure.models.user.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake user DAO.
 */
@Module
public class MockUserDaoModule {
    /**
     * Return the user DAO to be provided for all dependents.
     *
     * @param mockUserDao fake user DAO
     * @return user DAO to be used for user database access
     */
    @Provides
    public UserDao provideUserDao(MockUserDao mockUserDao) {
        return mockUserDao;
    }
}
