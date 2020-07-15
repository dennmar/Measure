package com.example.measure.features.register;

import android.os.Bundle;

import com.example.measure.models.user.UserRepository;
import com.squareup.inject.assisted.Assisted;
import com.squareup.inject.assisted.AssistedInject;

/**
 * A view model that handles the interaction between the register view
 * (fragment) and the model, using Dagger to satisfy dependencies.
 */
public class DaggerRegisterViewModel implements RegisterViewModel{
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
}
