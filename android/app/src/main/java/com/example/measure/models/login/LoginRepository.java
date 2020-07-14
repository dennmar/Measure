package com.example.measure.models.login;

import com.example.measure.models.data.User;
import com.example.measure.utils.AuthenticationException;

/**
 * A repository for accessing the login session data.
 */
public interface LoginRepository {
    /**
     * Retrieve the current user logged in.
     *
     * @return the user currently logged in
     */
    User getCurrentUser();

    /**
     * Start a login session for the user if the credentials are valid.
     *
     * @param username username of the user
     * @param password password of the user
     * @throws AuthenticationException if the username and password are invalid
     */
    void login(String username, String password)
            throws AuthenticationException;

    /**
     * Clear the current login session.
     */
    void logout();
}
