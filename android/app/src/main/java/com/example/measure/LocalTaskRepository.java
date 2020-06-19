package com.example.measure;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

/**
 * A repository using a local database for accessing the task data.
 */
public class LocalTaskRepository implements TaskRepository {
    @Override
    public LiveData<List<Task>> getSortedTasks(User user, Date startDate,
                                               Date endDate) {
        return null;
    }

    @Override
    public boolean addTask(User user, Task task) {
        return false;
    }

    @Override
    public boolean updateTask(User user, Task task) {
        return false;
    }

    @Override
    public boolean deleteTask(User user, Task task) {
        return false;
    }
}
