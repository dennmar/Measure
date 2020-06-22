package com.example.measure.models.task;

import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * A SQLite task database object.
 */
public class SQLiteTaskDao implements TaskDao {
    @Inject
    public SQLiteTaskDao() { }

    @Override
    public LiveData<List<Task>> getSortedTasks(User user, Date startDate, Date endDate) throws DBOperationException {
        return null;
    }

    @Override
    public void addTask(User user, Task task) throws DBOperationException {

    }

    @Override
    public void updateTask(User user, Task task) throws DBOperationException {

    }

    @Override
    public void deleteTask(User user, Task task) throws DBOperationException {

    }
}
