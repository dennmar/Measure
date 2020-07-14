package com.example.measure.models.login;

import android.util.Log;

import com.example.measure.models.data.User;
import com.example.measure.models.user.UserDao;
import com.example.measure.utils.AuthenticationException;

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

    /**
     * Retrieve the current user logged in.
     *
     * @return the user currently logged in
     */
    @Override
    public User getCurrentUser() {
        return loginDao.getCurrentUser();
    }

    /**
     * Start a login session for the user if the credentials are valid.
     *
     * @param username username of the user
     * @param password password of the user
     * @throws AuthenticationException if the username and password are invalid
     */
    @Override
    public void login(String username, String password)
            throws AuthenticationException {
        User loginUser = userDao.getUser(username, password);
        if (loginUser == null) {
            throw new AuthenticationException("Invalid username or password");
        }

        loginDao.setCurrentUser(loginUser);
    }

    /**
     * Clear the current login session.
     */
    @Override
    public void logout() {
       loginDao.clearSession();
    }
}
