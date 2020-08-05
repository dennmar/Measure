package com.example.measure.models.login;

import androidx.annotation.VisibleForTesting;

import com.example.measure.models.data.User;
import com.example.measure.models.user.UserDao;
import com.example.measure.utils.AuthenticationException;
import com.example.measure.utils.DBOperationException;

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
     * @throws DBOperationException    if there was an error searching for the
     *                                 user
     */
    @Override
    public void login(String username, String password)
            throws AuthenticationException, DBOperationException {
        User loginUser = userDao.asyncGetUser(username, password);
        
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

    /**
     * Add the user to the database.
     *
     * @param user user to be added
     * @throws IllegalArgumentException when the new user would be invalid
     * @throws DBOperationException     if the user could not be added
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void addUser(User user)
            throws IllegalArgumentException, DBOperationException {
        userDao.asyncAddUser(user);
    }
}
