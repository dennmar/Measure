package com.example.measure.features.register;

import android.os.Bundle;

import androidx.annotation.VisibleForTesting;

import com.example.measure.models.data.User;

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
     * @throws IllegalArgumentException when the new user would be invalid
     */
    void addUser(User newUser) throws IllegalArgumentException;

    /**
     * Return the error message.
     *
     * @return error message
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    String getErrorMessage();
}
