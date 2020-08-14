package com.example.measure.features.habit_tracker;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Habit;

import java.util.ArrayList;
import java.util.List;

/**
 * A fake view model that imitates the interaction between the habit tracker
 * view (fragment) and the model.
 */
public class MockHabitTrackerViewModel implements HabitTrackerViewModel {
    private MutableLiveData<List<Habit>> habitsLiveData;
    private List<Habit> habitsList;

    /**
     * Initialize member variables.
     *
     * @param savedInstanceState previously saved state of the view model
     */
    private MockHabitTrackerViewModel(Bundle savedInstanceState) {
        habitsLiveData = new MutableLiveData<>(new ArrayList<>());
        habitsList = new ArrayList<>();
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

    /**
     * Retrieve all habits for the user.
     *
     * @return observable list of habits belonging to the user
     */
    public LiveData<List<Habit>> getHabits() {
        return habitsLiveData;
    }

    /**
     * Add a habit for the user.
     *
     * @param habit habit to be added
     */
    public void addHabit(Habit habit) {
        habitsList.add(habit);
        habitsLiveData.setValue(habitsList);
    }
}
