package com.example.measure.models.user;

import com.example.measure.models.data.User;

import javax.inject.Inject;

/**
 * A SQLite user database access object.
 */
public class SQLiteUserDao implements UserDao {
    @Inject
    public SQLiteUserDao() {

    }

    @Override
    public void addUser(User user, String password) {

    }
}
