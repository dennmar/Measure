package com.example.measure.models.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;
import com.example.measure.utils.InvalidQueryException;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A repository using a local database for accessing the task data.
 */
public class LocalTaskRepository implements TaskRepository {
    private TaskDao taskDao;
    private MutableLiveData<List<Task>> sortedTasks;

    /**
     * Initialize member variables.
     *
     * @param taskDao data access object for tasks
     */
    @Inject
    public LocalTaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
        sortedTasks = new MutableLiveData<>(new ArrayList<>());
    }

    /**
     * Retrieve all tasks for the user from the database within a date range in
     * ascending order by date.
     *
     * @param user      user to retrieve tasks for
     * @param startDate starting date of tasks (inclusive. no time zone)
     * @param endDate   ending date of tasks (exclusive, no time zone)
     * @return observable list of tasks belonging to the user sorted by date
     * @throws DBOperationException  if the tasks could not be fetched
     * @throws InvalidQueryException if end date comes before the start date
     */
    public LiveData<List<Task>> getSortedTasks(User user, LocalDate startDate,
            LocalDate endDate) throws DBOperationException,
            InvalidQueryException {
        if (startDate.equals(endDate)) {
            return this.sortedTasks;
        }
        else if (endDate.compareTo(startDate) < 0) {
            throw new InvalidQueryException("End date (" + endDate + ") is " +
                    "earlier than start date (" + startDate + ")");
        }

        try {
            taskDao.getSortedTasks(user, startDate, endDate)
                    .observeForever(sortedTasks -> {
                        this.sortedTasks.setValue(sortedTasks);
            });
        }
        catch (DBOperationException e) {
            throw(e);
        }
        finally {
            return this.sortedTasks;
        }
    }

    /**
     * Store a task for the user in the database.
     *
     * @param user user who will own the task
     * @param task task to store for the user
     * @return true if the operation was successful; false otherwise
     * @throws DBOperationException  if the task could not be added
     * @throws InvalidQueryException if the task to add does not belong to
     *                               the user
     */
    public boolean addTask(User user, Task task) throws DBOperationException,
            InvalidQueryException {
        if (user.getId() != task.getUserId()) {
            throw new InvalidQueryException("Task to be added (" + task +
                    ") does not match user ID of user (" + user + ")");
        }

        taskDao.addTask(user, task);
        return true;
    }

    /**
     * Update a task for the user in the database.
     *
     * @param user user who owns the task to update
     * @param task updated task to set for the user
     * @return true if the operation was successful; false otherwise
     * @throws DBOperationException  if the task could not be updated
     * @throws InvalidQueryException if the task to update does not belong to
     *                               the user
     */
    public boolean updateTask(User user, Task task)
            throws DBOperationException, InvalidQueryException {
        if (user.getId() != task.getUserId()) {
            throw new InvalidQueryException("Task to be updated (" + task +
                    ") does not match user ID of user (" + user + ")");
        }

        taskDao.updateTask(user, task);
        return true;
    }

    /**
     * Delete a task for the user in the database.
     *
     * @param user user who owns the task to delete
     * @param task task to delete
     * @return true if the operation was successful; false otherwise
     * @throws DBOperationException  if the task could not be deleted
     * @throws InvalidQueryException if the task to delete does not belong to
     *                               the user
     */
    public boolean deleteTask(User user, Task task)
            throws DBOperationException, InvalidQueryException {
        if (user.getId() != task.getUserId()) {
            throw new InvalidQueryException("Task to be deleted (" + task +
                    ") does not match user ID of user (" + user + ")");
        }

        taskDao.deleteTask(user, task);
        return true;
    }
}
