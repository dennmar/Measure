package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestTaskDaoComponent;
import com.example.measure.di.components.TestTaskDaoComponent;
import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.models.task.TaskDao;
import com.example.measure.utils.DBOperationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test the task DAO.
 */
public class TaskDaoTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    TaskDao taskDao;

    /**
     * Create a new task DAO.
     */
    @Before
    public void initTaskDao() {
        TestTaskDaoComponent taskDaoComponent =
                DaggerTestTaskDaoComponent.create();
        taskDao = taskDaoComponent.taskDao();
    }

    /**
     * Test getting tasks when none exists for the user.
     */
    @Test
    public void getEmptyTasks() {
        User testUser = new User(1, "test", null);
        Date startDate = new Date(200);
        Date endDate = new Date(10000);

        List<Task> expectedTasks = new ArrayList<>();
        try {
            List<Task> getResult = taskDao.getSortedTasks(testUser, startDate,
                    endDate).getValue();
            assertThat(getResult, equalTo(expectedTasks));
        }
        catch (DBOperationException e){
            assertThat(false, equalTo(true));
        }
    }
}
