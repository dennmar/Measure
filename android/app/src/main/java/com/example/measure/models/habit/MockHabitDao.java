package com.example.measure.models.habit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Habit;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * A fake habit database access object.
 */
public class MockHabitDao implements HabitDao {
    private static HashMap<Long, List<Habit>> userHabitsMap;
    private MutableLiveData<List<Habit>> habits;

    // Used to aid MockHabitCompletionDao in adding habit completions.
    public static User lastUser;

    /**
     * Initialize member variables.
     */
    @Inject
    public MockHabitDao() {
        habits = new MutableLiveData<>(new ArrayList<>());
        userHabitsMap = new HashMap<>();
        lastUser = null;
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
        lastUser = user;
    }

    /**
     * Add a habit completion.
     *
     * @param user            user who owns the habit that was completed
     * @param habitCompletion completion info for the habit
     * @throws DBOperationException
     */
    public static void addHabitCompletion(User user,
            HabitCompletion habitCompletion) throws DBOperationException {
        if (user == null || !userHabitsMap.containsKey(user.getId())) {
            throw new DBOperationException("User (" + user + ") not found");
        }

        List<Habit> userHabits = userHabitsMap.get(user.getId());

        for (Habit h : userHabits) {
            if (h.getId() == habitCompletion.getHabitId()) {
                h.getCompletions()
                        .add(habitCompletion.getLocalCompletionDate());
                return;
            }
        }

        throw new DBOperationException("Habit with id "
                + habitCompletion.getHabitId() + " not found");
    }
}
