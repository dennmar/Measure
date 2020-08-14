package com.example.measure.features.habit_tracker;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.features.ViewModel;
import com.example.measure.models.data.Habit;
import com.example.measure.models.data.User;
import com.example.measure.models.habit.HabitRepository;
import com.example.measure.models.login.LoginRepository;
import com.squareup.inject.assisted.Assisted;
import com.squareup.inject.assisted.AssistedInject;

import java.util.ArrayList;
import java.util.List;

/**
 * A view model that handles the interaction between the habit tracker view
 * (fragment) and the model, using Dagger to satisfy dependencies.
 */
public class DaggerHabitTrackerViewModel extends ViewModel
        implements HabitTrackerViewModel {
    private HabitRepository habitRepo;
    private LoginRepository loginRepo;
    private User currUser;
    private MutableLiveData<List<Habit>> habits;

    /**
     * Initialize member variables.
     *
     * @param savedInstanceState previously saved state of the view model
     */
    @AssistedInject
    public DaggerHabitTrackerViewModel(HabitRepository habitRepo,
            LoginRepository loginRepo, @Assisted Bundle savedInstanceState) {
        this.habitRepo = habitRepo;
        this.loginRepo = loginRepo;
        currUser = getCurrentUser();
        habits = new MutableLiveData<>(new ArrayList<>());
    }

    /**
     * Factory for creating a dagger habit tracker view model using assisted
     * injection.
     */
    @AssistedInject.Factory
    public interface Factory extends HabitTrackerViewModel.Factory {
        /**
         * Create an instance of a habit tracker view model with the given
         * saved instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created habit tracker view model
         */
        HabitTrackerViewModel create(Bundle savedInstanceState);
    }

    /**
     * Get the currently logged in user.
     *
     * @return the currently logged in user
     */
    private User getCurrentUser() {
        return null;
    }

    /**
     * Retrieve all habits for the user.
     *
     * @return observable list of habits belonging to the user
     */
    public LiveData<List<Habit>> getHabits() {
        return null;
    }

    /**
     * Add a habit for the user.
     *
     * @param habit habit to be added
     */
    public void addHabit(Habit habit) {}
}
