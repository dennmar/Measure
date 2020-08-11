package com.example.measure.features.register;

import android.os.Bundle;

import androidx.annotation.VisibleForTesting;

import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

/**
 * A view model that handles the interaction between the register view
 * (fragment) and the model.
 */
public interface RegisterViewModel {
    /**
     * Factory for creating a register view model.
     */
    interface Factory {
        /**
         * Create an instance of a register view model with the given saved
         * instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created register view model
         */
        RegisterViewModel create(Bundle savedInstanceState);
    }

    /**
     * Add a new user.
     *
     * @param newUser  new user to be added
     * @throws DBOperationException if the new user could not be added
     * @throws IllegalArgumentException when the new user would be invalid
     */
    void addUser(User newUser)
            throws DBOperationException, IllegalArgumentException;

    /**
     * Return the user with the matching username and password.
     *
     * @param username username of the user to fetch
     * @param password password of the user to fetch
     * @return the matching user or null if no such user was found
     * @throws DBOperationException if the user could not be fetched
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    User getUser(String username, String password) throws DBOperationException;
}
