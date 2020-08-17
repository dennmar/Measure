package com.example.measure.models.habit;

import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Habit;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.List;

import javax.inject.Inject;

/**
 * A SQLite habit database object.
 */
public class SQLiteHabitDao implements HabitDao {
    @Inject
    public SQLiteHabitDao() {

    }

    @Override
    public LiveData<List<Habit>> getHabits(User user) throws DBOperationException {
        return null;
    }

    @Override
    public void addHabit(User user, Habit habit) throws DBOperationException {

    }
}
