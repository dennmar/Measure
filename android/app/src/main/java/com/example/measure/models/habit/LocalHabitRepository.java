package com.example.measure.models.habit;

import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Habit;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.List;

import javax.inject.Inject;

/**
 * A repository using a local database for accessing the habit data.
 */
public class LocalHabitRepository implements HabitRepository {
    private HabitDao habitDao;

    /**
     * Initialize member variables.
     */
    @Inject
    public LocalHabitRepository(HabitDao habitDao) {
        this.habitDao = habitDao;
    }

    @Override
    public MutableLiveData<List<Habit>> getHabits(User user)
            throws DBOperationException {
        return null;
    }

    @Override
    public void addHabit(User user, Habit habit) throws DBOperationException {

    }
}
