package com.example.measure.di.modules.prod;

import com.example.measure.models.user.SQLiteUserDao;
import com.example.measure.models.user.UserDao;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the user DAO.
 */
@Module
public class UserDaoModule {
    /**
     * Return the user DAO to be provided for all dependents.
     *
     * @param sqLiteUserDao user DAO that interacts with SQLite database
     * @return user DAO to be used for user database access
     */
    @Provides
    public UserDao provideUserDao(SQLiteUserDao sqLiteUserDao) {
        return sqLiteUserDao;
    }
}
