package com.example.measure.models.login;

import com.example.measure.models.data.User;
import com.example.measure.models.user.UserDao;

import javax.inject.Inject;

/**
 * A repository using a local database for accessing the login session data.
 */
public class LocalLoginRepository implements LoginRepository {
    private LoginDao loginDao;
    private UserDao userDao;

    /**
     * Initialize member variables.
     *
     * @param loginDao data access object for the login session
     * @param userDao  data access object for users
     */
    @Inject
    public LocalLoginRepository(LoginDao loginDao, UserDao userDao) {
        this.loginDao = loginDao;
        this.userDao = userDao;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public void login(String username, String password) {}

    @Override
    public void logout() {}
}
