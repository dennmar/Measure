package com.example.measure.models.task;

import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * A repository using a local database for accessing the task data.
 */
public class LocalTaskRepository implements TaskRepository {
    private TaskDao taskDao;

    /**
     * Initialize member variables.
     *
     * @param taskDao data access object for tasks
     */
    @Inject
    public LocalTaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    /**
     * Retrieve all tasks for the user from the database within a date range in
     * ascending order by date.
     *
     * @param user      user to retrieve tasks for
     * @param startDate starting date of tasks to fetch (inclusive)
     * @param endDate   ending date of tasks to fetch (exclusive)
     * @return observable list of tasks belonging to the user sorted by date
     */
    public LiveData<List<Task>> getSortedTasks(User user, Date startDate,
                                               Date endDate) {
        return null;
    }

    /**
     * Store a task for the user in the database.
     *
     * @param user user who will own the task
     * @param task task to store for the user
     * @return true if the operation was successful; false otherwise
     */
    public boolean addTask(User user, Task task) {
        return false;
    }

    /**
     * Update a task for the user in the database.
     *
     * @param user user who owns the task to update
     * @param task updated task to set for the user
     * @return true if the operation was successful; false otherwise
     */
    public boolean updateTask(User user, Task task) {
        return false;
    }

    /**
     * Delete a task for the user in the database.
     *
     * @param user user who owns the task to delete
     * @param task task to delete
     * @return true if the operation was successful; false otherwise
     */
    public boolean deleteTask(User user, Task task) {
        return false;
    }
}
