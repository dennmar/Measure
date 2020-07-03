package com.example.measure.models.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;
import com.example.measure.utils.SortByDate;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * A fake task database object.
 */
public class MockTaskDao implements TaskDao {
    private HashMap<Integer, List<Task>> userTaskMap;
    private MutableLiveData<List<Task>> sortedTasks;
    private Comparator<Task> sortByDate;

    @Inject
    public MockTaskDao() {
        userTaskMap = new HashMap<>();
        sortedTasks = new MutableLiveData<>();
        sortByDate = new SortByDate();
    }

    /**
     * Retrieve all tasks for the user from the database within a date range in
     * ascending order by date.
     *
     * @param user      user to retrieve tasks for
     * @param startDate starting date of tasks (inclusive. no time zone)
     * @param endDate   ending date of tasks (exclusive, no time zone)
     * @throws DBOperationException if the tasks could not be fetched
     */
    public LiveData<List<Task>> getSortedTasks(User user, LocalDate startDate,
                                        LocalDate endDate) {
        List<Task> sortedTaskList = new ArrayList<>();

        if (userTaskMap.containsKey(user.getId())) {
            List<Task> tasks = userTaskMap.get(user.getId());
            Collections.sort(tasks, sortByDate);
            for (Task task : tasks) {
                if (task.getLocalDueDate().compareTo(startDate) >= 0
                        && task.getLocalDueDate().compareTo(endDate) < 0) {
                    sortedTaskList.add(task);
                }
                else if (task.getLocalDueDate().compareTo(endDate) >= 0) {
                    break;
                }
            }
        }

        sortedTasks.setValue(sortedTaskList);
        return sortedTasks;
    }

    /**
     * Store a task for the user in the database.
     *
     * @param user user who will own the task
     * @param task task to store for the user
     */
    public void addTask(User user, Task task) {
        if (userTaskMap.containsKey(user.getId())) {
            userTaskMap.get(user.getId()).add(task);
        }
        else {
            List<Task> newUserTasks = new ArrayList<>();
            newUserTasks.add(task);
            userTaskMap.put(user.getId(), newUserTasks);
        }
    }

    /**
     * Update a task for the user in the database.
     *
     * @param user user who owns the task to update
     * @param task updated task to set for the user
     * @throws DBOperationException if the user or task does not exist
     */
    public void updateTask(User user, Task task) throws DBOperationException {
        if (userTaskMap.containsKey(user.getId())) {
            List<Task> userTasks = userTaskMap.get(user.getId());
            boolean foundTask = false;
            int i = 0;

            for (Task storedTask : userTaskMap.get(user.getId())) {
                if (storedTask.getId() == task.getId()) {
                    userTasks.set(i, task);
                    foundTask = true;
                    break;
                }
                i++;
            };

            if (!foundTask) {
                throw new DBOperationException("Task not found");
            }
        }
        else {
            throw new DBOperationException("User not found");
        }
    }

    /**
     * Delete a task for the user in the database.
     *
     * @param user user who owns the task to delete
     * @param task task to delete
     * @throws DBOperationException if the user or task does not exist
     */
    public void deleteTask(User user, Task task) throws DBOperationException {
        if (userTaskMap.containsKey(user.getId())) {
            List<Task> userTasks = userTaskMap.get(user.getId());
            boolean foundTask = false;
            int i = 0;

            for (Task storedTask : userTaskMap.get(user.getId())) {
                if (storedTask.getId() == task.getId()) {
                    userTasks.remove(i);
                    foundTask = true;
                    break;
                }
                i++;
            };

            if (!foundTask) {
                throw new DBOperationException("Task not found");
            }
        }
        else {
            throw new DBOperationException("User not found");
        }
    }
}
