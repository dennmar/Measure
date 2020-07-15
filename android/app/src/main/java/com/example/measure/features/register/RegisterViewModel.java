package com.example.measure.features.register;

import android.os.Bundle;

import com.example.measure.features.agenda.viewmodel.AgendaViewModel;

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
}
