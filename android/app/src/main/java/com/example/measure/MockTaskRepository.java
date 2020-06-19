package com.example.measure;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A fake repository for accessing task data.
 */
public class MockTaskRepository implements TaskRepository {
    private List<Task> tasks;
    private MutableLiveData<List<Task>> sortedTasks;
    private Comparator<Task> sortByDate;

    /**
     * Initialize the list of tasks.
     */
    public MockTaskRepository() {
        tasks = new ArrayList<>();
        sortedTasks = new MutableLiveData<>();
        sortByDate = new SortByDate();
    }

    /**
     * Sort tasks by date in ascending order.
     */
    private class SortByDate implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.localDueDate.compareTo(o2.localDueDate);
        }
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
    @Override
    public LiveData<List<Task>> getSortedTasks(User user, Date startDate,
                                               Date endDate) {
        List<Task> sortedTaskList = new ArrayList<>();
        Collections.sort(tasks, sortByDate);

        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.get(i);
            long currTaskTime = currTask.localDueDate.getTime();
            if (currTask.userId == user.id && currTaskTime >= startDate.getTime()
                    && currTaskTime < endDate.getTime()) {
                sortedTaskList.add(currTask);
            }
            else if (currTaskTime >= endDate.getTime()) {
                break;
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
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public boolean addTask(User user, Task task) {
        if (user.id != task.userId) {
            return false;
        }
        else {
            tasks.add(task);
            return true;
        }
    }

    /**
     * Update a task for the user in the database.
     *
     * @param user user who owns the task to update
     * @param task updated task to set for the user
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public boolean updateTask(User user, Task task) {
        if (user.id != task.userId) {
            return false;
        }
        else {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).id == task.id) {
                    tasks.set(i, task);
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Delete a task for the user in the database.
     *
     * @param user user who owns the task to delete
     * @param task task to delete
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public boolean deleteTask(User user, Task task) {
        if (user.id != task.userId) {
            return false;
        }
        else {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).id == task.id) {
                    tasks.remove(i);
                    return true;
                }
            }

            return false;
        }
    }
}
