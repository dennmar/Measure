package com.example.measure.models.login;

import com.example.measure.models.data.User;

import javax.inject.Inject;

/**
 * A shared preferences access object for the login session.
 */
public class SharedPrefsLoginDao implements LoginDao {
    @Inject
    public SharedPrefsLoginDao() {

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
