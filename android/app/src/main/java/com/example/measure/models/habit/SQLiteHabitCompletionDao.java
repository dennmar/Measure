package com.example.measure.models.habit;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.db.RoomHabitCompletion;
import com.example.measure.db.RoomHabitCompletionDao;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.utils.DBOperationException;

import javax.inject.Inject;

/**
 * A SQLite habit completion database access object.
 */
public class SQLiteHabitCompletionDao implements HabitCompletionDao {
    private RoomHabitCompletionDao roomHabitCompDao;

    /**
     * Initialize member variables.
     *
     * @param db SQLite database where habit completions are stored
     */
    @Inject
    public SQLiteHabitCompletionDao(MeasureRoomDatabase db) {
        roomHabitCompDao = db.habitCompletionDao();
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
