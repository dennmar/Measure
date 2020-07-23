package com.example.measure.di.modules.prod;

import com.example.measure.di.Scopes;
import com.example.measure.models.login.LoginDao;
import com.example.measure.models.login.SharedPrefsLoginDao;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the login DAO.
 */
@Module
public class LoginDaoModule {
    /**
     * Return the login DAO to be provided for all dependents.
     *
     * @param sharedPrefsLoginDao login DAO using shared preferences
     * @return login DAO to be used for login session data access
     */
    @Provides
    @Scopes.ApplicationScope
    public LoginDao provideLoginDao(SharedPrefsLoginDao sharedPrefsLoginDao) {
        return sharedPrefsLoginDao;
    }
}
