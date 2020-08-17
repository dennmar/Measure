package com.example.measure.models.habit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Habit;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A fake repository for accessing habit data.
 */
public class MockHabitRepository implements HabitRepository {
    List<Habit> allHabits;
    MutableLiveData<List<Habit>> habits;

    /**
     * Initialize member variables.
     */
    @Inject
    public MockHabitRepository() {
        allHabits = new ArrayList<>();
        habits = new MutableLiveData<>(new ArrayList<>());
    }

    /**
     * Retrieve all the habits for the user.
     *
     * @param user user to retrieve habits for
     * @return all the habits belonging to the user
     */
    @Override
    public LiveData<List<Habit>> getHabits(User user) {
        List<Habit> matchingHabits = new ArrayList<>();

        for (int i = 0; i < allHabits.size(); i++) {
            Habit currHabit = allHabits.get(i);
            if (currHabit.getUserId() == user.getId()) {
                matchingHabits.add(currHabit);
            }
        }

        habits.setValue(matchingHabits);
        return habits;
    }

    /**
     * Store a habit for the user.
     *
     * @param user user who will own the habit
     * @param habit habit to store for the user
     */
    @Override
    public void addHabit(User user, Habit habit) {
        allHabits.add(habit);
    }
}
