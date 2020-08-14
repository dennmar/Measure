package com.example.measure.features.habit_tracker;

import android.os.Bundle;

/**
 * A view model that handles the interaction between the habit tracker view
 * (fragment) and the model.
 */
public interface HabitTrackerViewModel {
    /**
     * Factory for creating a habit tracker view model.
     */
    interface Factory {
        /**
         * Create an instance of a habit tracker model with the given saved
         * instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created habit tracker view model
         */
        HabitTrackerViewModel create(Bundle savedInstanceState);
    }
}
