package com.example.measure.features.habit_tracker;

import android.os.Bundle;

/**
 * A fake view model that imitates the interaction between the habit tracker
 * view (fragment) and the model.
 */
public class MockHabitTrackerViewModel implements HabitTrackerViewModel {
    /**
     * Initialize member variables.
     *
     * @param savedInstanceState previously saved state of the view model
     */
    private MockHabitTrackerViewModel(Bundle savedInstanceState) {

    }

    /**
     * Factory for creating a fake habit tracker view model.
     */
    public static class Factory implements HabitTrackerViewModel.Factory {
        /**
         * Create an instance of a fake habit tracker view model with the given
         * saved instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created habit tracker view model
         */
        public HabitTrackerViewModel create(Bundle savedInstanceState) {
            return new MockHabitTrackerViewModel(savedInstanceState);
        }
    }
}
