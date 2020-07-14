package com.example.measure.models.user;

import com.example.measure.models.data.User;

import javax.inject.Inject;

/**
 * A fake user database access object.
 */
public class MockUserDao implements UserDao {
    @Inject
    public MockUserDao() {

    }

    @Override
    public void addUser(User user, String password) {

    }
}
