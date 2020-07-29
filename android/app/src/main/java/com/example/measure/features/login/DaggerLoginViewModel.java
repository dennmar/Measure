package com.example.measure.features.login;

import android.os.Bundle;

import androidx.annotation.VisibleForTesting;

import com.example.measure.models.login.LoginRepository;
import com.example.measure.utils.AuthenticationException;
import com.example.measure.utils.DBOperationException;
import com.squareup.inject.assisted.Assisted;
import com.squareup.inject.assisted.AssistedInject;

/**
 * A view model that handles the interaction between the login view (fragment)
 * and the model, using Dagger to satisfy dependencies.
 */
public class DaggerLoginViewModel implements LoginViewModel {
    private LoginRepository loginRepo;

    /**
     * Initialize member variables.
     *
     * @param loginRepo          repository for accessing login data
     * @param savedInstanceState previously saved state of the view model
     */
    @AssistedInject
    public DaggerLoginViewModel(LoginRepository loginRepo,
            @Assisted Bundle savedInstanceState) {
        this.loginRepo = loginRepo;
    }

    /**
     * Factory for creating a dagger login view model using assisted injection.
     */
    @AssistedInject.Factory
    public interface Factory extends LoginViewModel.Factory {
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
    public void login(String username, String password)
            throws AuthenticationException, DBOperationException {

    }

    /**
     * Add the user to the database.
     *
     * Note: this is not implemented on purpose.
     *
     * @param username username of the user to be added
     * @param password password of the user to be added
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void addUser(String username, String password) {}
}
