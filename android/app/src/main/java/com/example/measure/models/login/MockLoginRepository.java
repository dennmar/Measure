package com.example.measure.models.login;

import androidx.annotation.VisibleForTesting;

import com.example.measure.models.data.User;

/**
 * A fake repository for accessing the login session data.
 */
public class MockLoginRepository implements LoginRepository {
    public static final User DEFAULT_USER = new User(1, "Hello", null);
    private User currUser;

    /**
     * Initialize the current user.
     */
    public MockLoginRepository() {
        currUser = DEFAULT_USER;
    }

    /**
     * Retrieve the current user logged in.
     *
     * @return the user currently logged in
     */
    @Override
    public User getCurrentUser() {
        return currUser;
    }

    /**
     * Start a login session for the user.
     *
     * @param username username of the user
     * @param password password of the user
     */
    @Override
    public void login(String username, String password) {
        currUser = new User(1, username, null);
    }

    /**
     * Clear the current login session.
     */
    @Override
    public void logout() {
        currUser = null;
    }

    /**
     * Add the user to the database.
     *
     * Note: This is not implemented for the mock login repository on purpose.
     *
     * @param user user to be added
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void addUser(User user) {}
}
