package com.example.measure.models.habit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Habit;
import com.example.measure.models.data.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * A fake habit database access object.
 */
public class MockHabitDao implements HabitDao {
    private HashMap<Long, List<Habit>> userHabitsMap;
    private MutableLiveData<List<Habit>> habits;

    /**
     * Initialize member variables.
     */
    @Inject
    public MockHabitDao() {
        habits = new MutableLiveData<>(new ArrayList<>());
        userHabitsMap = new HashMap<>();
    }

    /**
     * Retrieve all habits for the user.
     *
     * @param user user to fetch habits for
     * @return observable list of all the user's habits
     */
    @Override
    public LiveData<List<Habit>> getHabits(User user) {
        if (userHabitsMap.containsKey(user.getId())) {
            habits.setValue(userHabitsMap.get(user.getId()));
        }
        else {
            habits.setValue(new ArrayList<>());
        }

        return habits;
    }

    /**
     * Store a habit for the user.
     *
     * @param user  user who will own the habit
     * @param habit habit to store for the user
     */
    @Override
    public void addHabit(User user, Habit habit) {
        if (!userHabitsMap.containsKey(user.getId())) {
            userHabitsMap.put(user.getId(), new ArrayList<>());
        }

        userHabitsMap.get(user.getId()).add(habit);
    }
}
