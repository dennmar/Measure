package com.example.measure.models.login;

import com.example.measure.models.data.User;

/**
 * A database access object for the login session.
 */
public interface LoginDao {
    /**
     * Retrieve the current user logged in from the database.
     *
     * @return the user currently logged in
     */
    User getCurrentUser();

    /**
     * Set the current user in the database.
     *
     * @param user current user to set
     * @return true if the operation was successful; false otherwise
     */
    void setCurrentUser(User user);

    /**
     * Clear the current login session in the database.
     */
    void clearSession();
}
