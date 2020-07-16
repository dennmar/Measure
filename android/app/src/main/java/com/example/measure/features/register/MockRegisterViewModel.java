package com.example.measure.features.register;

import android.os.Bundle;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.User;

import java.util.HashMap;

/**
 * A fake view model that imitates the interaction between the register view
 * (fragment) and the model.
 */
public class MockRegisterViewModel implements RegisterViewModel {
    private HashMap<String, User> usernameUserMap;
    private MutableLiveData<String> errMsg;

    /**
     * Initialize member variables.
     *
     * @param savedInstanceState previously saved state of the view model
     */
    private MockRegisterViewModel(Bundle savedInstanceState) {
        usernameUserMap = new HashMap<>();
        errMsg = new MutableLiveData<>(null);
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
        else if (newUser.getUsername() == null) {
            throw new IllegalArgumentException("Username is null");
        }

        usernameUserMap.put(newUser.getUsername(), newUser);
        errMsg = null;
    }

    /**
     * Return the error message.
     *
     * @return error message
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public String getErrorMessage() {
        return errMsg.getValue();
    }
}
