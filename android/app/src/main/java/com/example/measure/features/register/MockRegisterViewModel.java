package com.example.measure.features.register;

import android.os.Bundle;

/**
 * A fake view model that imitates the interaction between the register view
 * (fragment) and the model.
 */
public class MockRegisterViewModel implements RegisterViewModel {
    private Bundle savedInstanceState;

    /**
     * Initialize member variables.
     *
     * @param savedInstanceState previously saved state of the view model
     */
    private MockRegisterViewModel(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
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
}
