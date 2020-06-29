package com.example.measure.models.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.db.RoomTask;
import com.example.measure.db.RoomTaskDao;
import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A SQLite task database object.
 */
public class SQLiteTaskDao implements TaskDao {
    private RoomTaskDao roomTaskDao;
    private MutableLiveData<List<Task>> sortedTasks;

    /**
     * Initialize member variables.
     *
     * @param db SQLite database where tasks are stored
     */
    @Inject
    public SQLiteTaskDao(MeasureRoomDatabase db) {
        roomTaskDao = db.taskDao();
        sortedTasks = new MutableLiveData<>();
    }

    /**
     * Retrieve all tasks for the user from the database within a date range in
     * ascending order by date.
     *
     * @param user      user to retrieve tasks for
     * @param startDate starting date of tasks to fetch (inclusive)
     * @param endDate   ending date of tasks to fetch (exclusive)
     * @throws DBOperationException if the tasks could not be fetched
     */
    public LiveData<List<Task>> getSortedTasks(User user, LocalDate startDate,
            LocalDate endDate) throws DBOperationException {
        return null;
    }

    /**
     * Store a task for the user in the database.
     *
     * @param user user who will own the task
     * @param task task to store for the user
     * @throws DBOperationException if the task could not be added
     */
    public void addTask(User user, Task task) throws DBOperationException {
    }

    /**
     * Update a task for the user in the database.
     *
     * @param user user who owns the task to update
     * @param task updated task to set for the user
     * @throws DBOperationException if the task does not exist
     */
    public void updateTask(User user, Task task) throws DBOperationException {
    }

    /**
     * Delete a task for the user in the database.
     *
     * @param user user who owns the task to delete
     * @param task task to delete
     * @throws DBOperationException if the task does not exist
     */
    public void deleteTask(User user, Task task) throws DBOperationException {
    }
}
