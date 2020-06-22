package com.example.measure.models.task;

import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.Date;
import java.util.List;

/**
 * A task database access object.
 */
public interface TaskDao {
    /**
     * Retrieve all tasks for the user from the database within a date range in
     * ascending order by date.
     *
     * @param user      user to retrieve tasks for
     * @param startDate starting date of tasks to fetch (inclusive)
     * @param endDate   ending date of tasks to fetch (exclusive)
     * @throws DBOperationException if the tasks could not be fetched
     */
    LiveData<List<Task>> getSortedTasks(User user, Date startDate,
            Date endDate) throws DBOperationException;

    /**
     * Store a task for the user in the database.
     *
     * @param user user who will own the task
     * @param task task to store for the user
     * @throws DBOperationException if the task could not be added
     */
    void addTask(User user, Task task) throws DBOperationException;

    /**
     * Update a task for the user in the database.
     *
     * @param user user who owns the task to update
     * @param task updated task to set for the user
     * @throws DBOperationException if the task does not exist
     */
    void updateTask(User user, Task task) throws DBOperationException;

    /**
     * Delete a task for the user in the database.
     *
     * @param user user who owns the task to delete
     * @param task task to delete
     * @throws DBOperationException if the task does not exist
     */
    void deleteTask(User user, Task task) throws DBOperationException;
}
