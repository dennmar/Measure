package com.example.measure.models.habit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Habit;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;
import com.example.measure.utils.InvalidQueryException;

import java.util.List;

import javax.inject.Inject;

/**
 * A repository using a local database for accessing the habit data.
 */
public class LocalHabitRepository implements HabitRepository {
    private HabitDao habitDao;
    private HabitCompletionDao habitCompDao;
    private MutableLiveData<List<Habit>> habits;

    /**
     * Initialize member variables.
     *
     * @param habitDao     database access object for habits
     * @param habitCompDao database access object for habit completions
     */
    @Inject
    public LocalHabitRepository(HabitDao habitDao,
            HabitCompletionDao habitCompDao) {
        this.habitDao = habitDao;
        this.habitCompDao = habitCompDao;
        habits = new MutableLiveData<>();
    }

    /**
     * Retrieve all the habits for the user from the database.
     *
     * @param user user to retrieve habits for
     * @return all the habits belonging to the user
     * @throws DBOperationException if the habits could not be fetched
     */
    @Override
    public LiveData<List<Habit>> getHabits(User user)
            throws DBOperationException {
        habitDao.getHabits(user).observeForever(allHabits -> {
            habits.setValue(allHabits);
        });

        return habits;
    }

    /**
     * Store a habit for the user in the database.
     *
     * @param user  user who will own the habit
     * @param habit habit to store for the user
     * @throws DBOperationException  if the habit could not be added
     * @throws InvalidQueryException if the habit to add does not belong to
     *                               the user
     */
    @Override
    public void addHabit(User user, Habit habit)
            throws DBOperationException, InvalidQueryException {
        if (user.getId() != habit.getUserId()) {
            throw new InvalidQueryException("Habit to be added (" + habit
                    + " does not match user ID of user (" + user + ")");
        }

        habitDao.addHabit(user, habit);
    }

    /**
     * Store a habit completion in the database.
     *
     * @param user            user who owns the habit
     * @param habit           habit that was completed
     * @param habitCompletion completion info for the habit
     * @throws DBOperationException if the habit completion could not be stored
     */
    @Override
    public void addHabitCompletion(User user, Habit habit,
            HabitCompletion habitCompletion) throws DBOperationException {

    }
}
