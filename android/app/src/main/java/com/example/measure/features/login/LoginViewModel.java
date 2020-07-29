package com.example.measure.features.login;

import android.os.Bundle;

import androidx.annotation.VisibleForTesting;

import com.example.measure.utils.AuthenticationException;
import com.example.measure.utils.DBOperationException;

/**
 * A view model that handles the interaction between the login view (fragment)
 * and the model.
 */
public interface LoginViewModel {
    /**
     * Factory for creating a login view model.
     */
    interface Factory {
        /**
         * Create an instance of a login view model with the given saved
         * instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created login view model
         */
        LoginViewModel create(Bundle savedInstanceState);
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
    void login(String username, String password)
            throws AuthenticationException, DBOperationException;

    /**
     * Add the user to the database.
     *
     * @param username username of the user to be added
     * @param password password of the user to be added
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    void addUser(String username, String password);
}
