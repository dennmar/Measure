package com.example.measure.models.login;

import com.example.measure.models.data.User;

/**
 * A fake repository for accessing the login session data.
 */
public class MockLoginRepository implements LoginRepository {
    public static final User DEFAULT_USER = new User(-1, "Hello", null);
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
     * Set the current user.
     *
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public boolean setCurrentUser(User user) {
        currUser = user;
        return true;
    }
}
