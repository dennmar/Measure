package com.example.measure.features.habit_tracker;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.features.ViewModel;
import com.example.measure.models.data.Habit;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.models.data.User;
import com.example.measure.models.habit.HabitRepository;
import com.example.measure.models.login.LoginRepository;
import com.example.measure.utils.DBOperationException;
import com.example.measure.utils.InvalidQueryException;
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
        try {
            return this.loginRepo.getCurrentUser();
        }
        catch (DBOperationException e) {
            Log.d("DHabitTrackerViewModel", "getCurrUser: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieve all habits for the user.
     *
     * @return observable list of habits belonging to the user
     * @throws DBOperationException if the habits could not be fetched
     */
    public LiveData<List<Habit>> getHabits() throws DBOperationException {
        return habitRepo.getHabits(currUser);
    }

    /**
     * Add a habit for the user.
     *
     * @param habit habit to be added
     * @throws DBOperationException if the habit could not be added
     */
    public void addHabit(Habit habit) throws DBOperationException {
        try {
            habitRepo.addHabit(currUser, habit);
        }
        catch (InvalidQueryException iqe) {
            Log.d("DHabitTrackerViewModel", iqe.getMessage());
        }
    }

    /**
     * Add a habit completion.
     *
     * @param habit           habit that was completed
     * @param habitCompletion completion info for the habit
     * @throws DBOperationException if the habit completion could not be added
     */
    @Override
    public void addHabitCompletion(Habit habit,
            HabitCompletion habitCompletion) throws DBOperationException {
        habitRepo.addHabitCompletion(currUser, habit, habitCompletion);
    }
}
