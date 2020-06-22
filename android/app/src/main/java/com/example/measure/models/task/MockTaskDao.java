package com.example.measure.models.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
     * @param startDate starting date of tasks to fetch (inclusive)
     * @param endDate   ending date of tasks to fetch (exclusive)
     */
    public LiveData<List<Task>> getSortedTasks(User user, Date startDate,
                                        Date endDate) {
        List<Task> sortedTaskList = new ArrayList<>();

        if (userTaskMap.containsKey(user.id)) {
            List<Task> tasks = userTaskMap.get(user.id);
            Collections.sort(tasks, sortByDate);
            for (Task task : tasks) {
                long taskTime = task.localDueDate.getTime();
                if (taskTime >= startDate.getTime()
                        && taskTime < endDate.getTime()) {
                    sortedTaskList.add(task);
                }
                else if (taskTime >= endDate.getTime()) {
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
        if (userTaskMap.containsKey(user.id)) {
            userTaskMap.get(user.id).add(task);
        }
        else {
            List<Task> newUserTasks = new ArrayList<>();
            newUserTasks.add(task);
            userTaskMap.put(user.id, newUserTasks);
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
        if (userTaskMap.containsKey(user.id)) {
            List<Task> userTasks = userTaskMap.get(user.id);
            boolean foundTask = false;
            int i = 0;

            for (Task storedTask : userTaskMap.get(user.id)) {
                if (storedTask.id == task.id) {
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
        if (userTaskMap.containsKey(user.id)) {
            List<Task> userTasks = userTaskMap.get(user.id);
            boolean foundTask = false;
            int i = 0;

            for (Task storedTask : userTaskMap.get(user.id)) {
                if (storedTask.id == task.id) {
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
