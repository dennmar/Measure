package com.example.measure.features.register;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.VisibleForTesting;

import com.example.measure.models.data.User;
import com.example.measure.models.user.UserRepository;
import com.example.measure.utils.DBOperationException;
import com.squareup.inject.assisted.Assisted;
import com.squareup.inject.assisted.AssistedInject;

/**
 * A view model that handles the interaction between the register view
 * (fragment) and the model, using Dagger to satisfy dependencies.
 */
public class DaggerRegisterViewModel implements RegisterViewModel {
    private UserRepository userRepo;
    private Bundle savedInstanceState;

    /**
     * Initialize all member variables and repositories for data access.
     */
    @AssistedInject
    public DaggerRegisterViewModel(UserRepository userRepo,
                                   @Assisted Bundle savedInstanceState) {
        this.userRepo = userRepo;
        this.savedInstanceState = savedInstanceState;
    }

    /**
     * Factory for creating a dagger register view model using assisted
     * injection.
     */
    @AssistedInject.Factory
    public interface Factory extends RegisterViewModel.Factory {
        /**
         * Create an instance of a register agenda view model with the given
         * saved instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created register view model
         */
        DaggerRegisterViewModel create(Bundle savedInstanceState);
    }

    /**
     * Add a new user.
     *
     * @param newUser  new user to be added
     */
    public void addUser(User newUser) {
        try {
            userRepo.addUser(newUser);
        }
        catch (DBOperationException e) {
            // TODO: handle exception
            Log.d("DaggerRegisterViewModel", e.getMessage());
        }
    }

    /**
     * Return the error message.
     *
     * @return error message
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public String getErrorMessage() {
        return null;
    }

    /**
     * Return the user with the matching username and password.
     *
     * @param username username of the user to fetch
     * @param password password of the user to fetch
     * @return the matching user or null if no such user was found
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public User getUser(String username, String password)
            throws DBOperationException {
        return userRepo.getUser(username, password);
    }
}
