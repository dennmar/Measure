package com.example.measure.models.habit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.db.RoomHabit;
import com.example.measure.db.RoomHabitCompletion;
import com.example.measure.db.RoomHabitCompletionDao;
import com.example.measure.db.RoomHabitWithCompletions;
import com.example.measure.db.RoomHabitDao;
import com.example.measure.models.data.Habit;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A SQLite habit database object.
 */
public class SQLiteHabitDao implements HabitDao {
    private RoomHabitDao roomHabitDao;
    private RoomHabitCompletionDao roomHabitCompDao;
    private MutableLiveData<List<Habit>> habits;

    /**
     * Initialize member variables.
     *
     * @param db SQLite database where the habits are stored
     */
    @Inject
    public SQLiteHabitDao(MeasureRoomDatabase db) {
        roomHabitDao = db.habitDao();
        roomHabitCompDao = db.habitCompletionDao();
        habits = new MutableLiveData<>(new ArrayList<>());
    }

    /**
     * Fetch the habit for the user.
     *
     * @param user  user who owns the habit
     * @param habit info about the habit to fetch
     * @return matching habit from the database
     * @throws DBOperationException if the habit could not be fetched
     */
    @Override
    public Habit getHabit(User user, Habit habit) throws DBOperationException {
        return null;
    }

    /**
     * Retrieve all habits for the user from the database.
     *
     * @param user user to fetch habits for
     * @return observable list of all the user's habits
     * @throws DBOperationException if the user's habits could not be fetched
     */
    @Override
    public LiveData<List<Habit>> getHabits(User user)
            throws DBOperationException {
        try {
            roomHabitDao.getHabits(user.getId()).observeForever(dbHabits -> {
                List<Habit> convertedHabits = new ArrayList<>();
                for (RoomHabitWithCompletions rhabit : dbHabits) {
                    convertedHabits.add(rhabit.toHabit());
                }
                habits.setValue(convertedHabits);
            });
        }
        catch (Exception e) {
            throw new DBOperationException(e.getMessage());
        }

        return habits;
    }

    /**
     * Store a habit for the user in the database.
     *
     * @param user  user who will own the habit
     * @param habit habit to store for the user
     * @throws DBOperationException if the habit could not be stored
     */
    @Override
    public void addHabit(User user, Habit habit) throws DBOperationException {
        RoomHabit rhabit = new RoomHabit(habit);

        try {
            roomHabitDao.insert(rhabit);
        }
        catch (Exception e) {
            throw new DBOperationException(e.getMessage());
        }
    }

    /**
     * Store a habit completion in the database.
     *
     * @param habitCompletion completion info for the habit
     * @throws DBOperationException if the habit completion could not be stored
     */
    @Override
    public void addHabitCompletion(HabitCompletion habitCompletion)
            throws DBOperationException {
        RoomHabitCompletion rhabitCompletion =
                new RoomHabitCompletion(habitCompletion);

        try {
            roomHabitCompDao.insert(rhabitCompletion);
        }
        catch (Exception e) {
            throw new DBOperationException(e.getMessage());
        }
    }
}
