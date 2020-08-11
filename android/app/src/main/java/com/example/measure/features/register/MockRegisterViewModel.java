package com.example.measure.features.register;

import android.os.Bundle;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.HashMap;
import java.util.Map;

/**
 * A fake view model that imitates the interaction between the register view
 * (fragment) and the model.
 */
public class MockRegisterViewModel implements RegisterViewModel {
    private HashMap<String, User> usernameUserMap;

    /**
     * Initialize member variables.
     *
     * @param savedInstanceState previously saved state of the view model
     */
    private MockRegisterViewModel(Bundle savedInstanceState) {
        usernameUserMap = new HashMap<>();
    }

    /**
     * Factory for creating a fake register view model.
     */
    public static class Factory implements RegisterViewModel.Factory {
        /**
         * Create an instance of a fake register view model with the given
         * saved instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created register view model
         */
        public MockRegisterViewModel create(Bundle savedInstanceState) {
            return new MockRegisterViewModel(savedInstanceState);
        }
    }

    /**
     * Add a new user.
     *
     * @param newUser  new user to be added
     */
    public void addUser(User newUser) throws IllegalArgumentException {
        if (newUser == null) {
            throw new IllegalArgumentException("User is null");
        }

        usernameUserMap.put(newUser.getUsername(), newUser);
    }

    /**
     * Return the user with the matching username and password.
     *
     * @param username username of the user to fetch
     * @param password password of the user to fetch
     * @return the matching user or null if no such user was found
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public User getUser(String username, String password) {
        for (Map.Entry<String, User> entry : usernameUserMap.entrySet()) {
            if (entry.getKey().equals(username)
                    && entry.getValue().getPassword().equals(password)) {
                return entry.getValue();
            }
        }

        return null;
    }
}
