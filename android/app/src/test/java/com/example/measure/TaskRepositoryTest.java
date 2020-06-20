package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestTaskRepositoryComponent;
import com.example.measure.di.components.TestTaskRepositoryComponent;
import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.models.task.TaskRepository;

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
 * Unit test the task repository.
 */
public class TaskRepositoryTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    TaskRepository taskRepo;

    /**
     * Create a new task respository.
     */
    @Before
    public void initTaskRepository() {
        TestTaskRepositoryComponent taskRepositoryComponent =
                DaggerTestTaskRepositoryComponent.create();
         taskRepo = taskRepositoryComponent.taskRepository();
    }

    @Test
    public void testGetEmptyTasks() {
        User testUser = new User(1, "test", null);
        Date startDate = new Date(0);
        Date endDate = new Date(1000000);

        List<Task> expectedTasks = new ArrayList<>();
        List<Task> getResult = taskRepo.getSortedTasks(testUser, startDate,
                endDate).getValue();
        assertThat(getResult, equalTo(expectedTasks));
    }
}
