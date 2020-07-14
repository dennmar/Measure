package com.example.measure.models.login;

import com.example.measure.models.data.User;

import javax.inject.Inject;

/**
 * A mock database access object for the login session.
 */
public class MockLoginDao implements LoginDao {
    private User currUser;

    /**
     * Initialize member variables.
     */
    @Inject
    public MockLoginDao() {
        currUser = null;
    }

    /**
     * Retrieve the current user logged in from the database.
     *
     * @return the user currently logged in
     */
    @Override
    public User getCurrentUser() {
        return currUser;
    }

    /**
     * Set the current user in the database.
     *
     * @param user current user to set
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public void setCurrentUser(User user) {
        currUser = user;
    }

    /**
     * Clear the current login session in the database.
     */
    @Override
    public void clearSession() {
        currUser = null;
    }
}
