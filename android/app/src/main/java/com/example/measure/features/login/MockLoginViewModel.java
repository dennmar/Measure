package com.example.measure.features.login;

import android.os.Bundle;

import androidx.annotation.VisibleForTesting;

import com.example.measure.utils.AuthenticationException;

import java.util.HashMap;
import java.util.Map;

/**
 * A fake view model that imitates the interaction between the login view
 * (fragment) and the model.
 */
public class MockLoginViewModel implements LoginViewModel {
    private HashMap<String, String> userPasswordMap;

    /**
     * Initialize member variables.
     *
     * @param savedInstanceState previously saved state of the view model
     */
    private MockLoginViewModel(Bundle savedInstanceState) {
        userPasswordMap = new HashMap<>();
    }

    /**
     * Factory for creating a login view model.
     */
    public static class Factory implements LoginViewModel.Factory {
        /**
         * Create an instance of a fake login view model with the given saved
         * instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created login view model
         */
        public LoginViewModel create(Bundle savedInstanceState) {
            return new MockLoginViewModel(savedInstanceState);
        }
    }

    /**
     * Start a login session for the user if the credentials are valid.
     *
     * @param username username of the user
     * @param password password of the user
     * @throws AuthenticationException if the username and password are invalid
     */
    public void login(String username, String password)
            throws AuthenticationException {
        for (Map.Entry<String, String> entry : userPasswordMap.entrySet()) {
            if (entry.getKey().equals(username)
                    && entry.getKey().equals(password)) {
                return;
            }
        }

        throw new AuthenticationException("Invalid username or password");
    }

    /**
     * Add the user to the database.
     *
     * @param username username of the user to be added
     * @param password password of the user to be added
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void addUser(String username, String password) {
        userPasswordMap.put(username, password);
    }
}
