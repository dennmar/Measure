package com.example.measure.models.login;

import com.example.measure.models.data.User;

import javax.inject.Inject;

/**
 * A repository using a local database for accessing the login session data.
 */
public class LocalLoginRepository implements LoginRepository {
    private LoginDao loginDao;

    @Inject
    public LocalLoginRepository(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public boolean setCurrentUser(User user) {
        return false;
    }

    @Override
    public void clearSession() { }
}
