package com.example.measure.models.login;

import com.example.measure.models.data.User;

import javax.inject.Inject;

/**
 * A mock database access object for the login session.
 */
public class MockLoginDao implements LoginDao {
    @Inject
    public MockLoginDao() {

    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public void setCurrentUser(User user) {

    }

    @Override
    public void clearSession() {

    }
}
