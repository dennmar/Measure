package com.example.measure.models.task;

import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;

import java.util.Date;
import java.util.List;

/**
 * A repository for accessing the task data.
 */
public interface TaskRepository {
    /**
     * Retrieve all tasks for the user from the database within a date range in
     * ascending order by date.
     *
     * @param user      user to retrieve tasks for
     * @param startDate starting date of tasks to fetch (inclusive)
     * @param endDate   ending date of tasks to fetch (exclusive)
     * @return observable list of tasks belonging to the user sorted by date
     */
    LiveData<List<Task>> getSortedTasks(User user, Date startDate,
                                        Date endDate);

    /**
     * Store a task for the user in the database.
     *
     * @param user user who will own the task
     * @param task task to store for the user
     * @return true if the operation was successful; false otherwise
     */
    boolean addTask(User user, Task task);

    /**
     * Update a task for the user in the database.
     *
     * @param user user who owns the task to update
     * @param task updated task to set for the user
     * @return true if the operation was successful; false otherwise
     */
    boolean updateTask(User user, Task task);

    /**
     * Delete a task for the user in the database.
     *
     * @param user user who owns the task to delete
     * @param task task to delete
     * @return true if the operation was successful; false otherwise
     */
    boolean deleteTask(User user, Task task);
}
