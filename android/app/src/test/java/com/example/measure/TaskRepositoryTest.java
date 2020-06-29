package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestTaskRepositoryComponent;
import com.example.measure.di.components.TestTaskRepositoryComponent;
import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.models.task.SortByDate;
import com.example.measure.models.task.TaskRepository;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * Test getting tasks when none exists for the user.
     */
    @Test
    public void testGetEmptyTasks() {
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(1999, 1, 1);
        LocalDate endDate = new LocalDate(2000, 1, 11);

        List<Task> expectedTasks = new ArrayList<>();
        List<Task> getResult = taskRepo.getSortedTasks(testUser, startDate,
                endDate).getValue();
        assertThat(getResult, equalTo(expectedTasks));
    }

    /**
     * Test adding a single task for a user.
     */
    @Test
    public void testAddTask() {
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(2001, 5, 5);
        LocalDate endDate = startDate.plusMonths(6);
        Task task = new Task();
        task.id = 1;
        task.userId = testUser.id;
        task.localDueDate = new LocalDate(startDate);

        boolean addResult = taskRepo.addTask(testUser, task);
        assertThat(addResult, equalTo(true));

        List<Task> expectedGetResult = new ArrayList<>();
        expectedGetResult.add(task);
        List<Task> getResult = taskRepo.getSortedTasks(testUser, startDate,
                endDate).getValue();
        assertThat(getResult, equalTo(expectedGetResult));
    }

    /**
     * Test adding multiple different tasks for a user and retrieving them.
     */
    @Test
    public void testGetMultipleDiffTasks() {
        List<Task> expectedGetResult = new ArrayList<>();
        int taskAmt = 10;
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(2007, 12, 23);
        LocalDate endDate = startDate.plusMonths(10);

        for (int i = taskAmt - 1; i >= 0; i--) {
            Task task = new Task();
            task.id = taskAmt - i;
            task.userId = testUser.id;
            task.localDueDate = startDate.plusDays(i);
            expectedGetResult.add(task);

            boolean addResult = taskRepo.addTask(testUser, task);
            assertThat(addResult, equalTo(true));
        }

        List<Task> getResult = taskRepo.getSortedTasks(testUser, startDate,
                endDate).getValue();
        Collections.sort(expectedGetResult, new SortByDate());
        assertThat(getResult, equalTo(expectedGetResult));
    }

    /**
     * Test getting a subset of a user's task within a date range.
     */
    @Test
    public void testGetSubset() {
        List<Task> expectedGetResult = new ArrayList<>();
        int taskAmt = 10;
        User testUser = new User(1, "test", null);
        LocalDate dateOffset = new LocalDate(2014, 2, 2);
        LocalDate startDate = dateOffset.plusDays(taskAmt / 2);
        LocalDate endDate = dateOffset.plusDays(taskAmt - 1);

        for (int i = 0; i < taskAmt; i++) {
            Task task = new Task();
            task.id = i + 1;
            task.userId = testUser.id;
            task.localDueDate = dateOffset.plusDays(taskAmt - i - 1);

            if (task.localDueDate.compareTo(startDate) >= 0
                    && task.localDueDate.compareTo(endDate) < 0) {
                expectedGetResult.add(task);
            }

            boolean addResult = taskRepo.addTask(testUser, task);
            assertThat(addResult, equalTo(true));
        }

        List<Task> getResult = taskRepo.getSortedTasks(testUser, startDate,
                endDate).getValue();
        Collections.sort(expectedGetResult, new SortByDate());
        assertThat(getResult, equalTo(expectedGetResult));
    }

    /**
     * Test getting a date range with no tasks.
     */
    @Test
    public void testGetNoMatchingTasks() {
        List<Task> expectedGetResult = new ArrayList<>();
        int taskAmt = 10;
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(2019, 12, 3);
        LocalDate endDate = startDate.plusYears(2);

        for (int i = taskAmt; i > 0; i--) {
            Task task = new Task();
            task.id = taskAmt - i + 1;
            task.userId = testUser.id;
            if (i % 2 == 0) {
                task.localDueDate = startDate.minusDays(i + 1);
            }
            else {
                task.localDueDate = endDate.plusDays(i + 1);
            }

            boolean addResult = taskRepo.addTask(testUser, task);
            assertThat(addResult, equalTo(true));
        }

        List<Task> getResult = taskRepo.getSortedTasks(testUser, startDate,
                endDate).getValue();
        assertThat(getResult, equalTo(expectedGetResult));
    }

    /**
     * Test editing a task for a user.
     */
    @Test
    public void testEditTask() {
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(2014, 4, 2);
        LocalDate endDate = startDate.plusMonths(4);

        Task task = new Task();
        task.id = 1;
        task.userId = testUser.id;
        task.localDueDate = endDate.minusDays(1);
        task.name = "Why";

        boolean addSuccess = taskRepo.addTask(testUser, task);
        assertThat(addSuccess, equalTo(true));

        List<Task> expectedGetResult1 = new ArrayList<>();
        expectedGetResult1.add(task);
        List<Task> actualGetResult1 = taskRepo.getSortedTasks(testUser,
                startDate, endDate).getValue();
        assertThat(actualGetResult1, equalTo(expectedGetResult1));

        Task editedTask = new Task();
        editedTask.id = 1;
        editedTask.userId = testUser.id;
        editedTask.localDueDate = endDate.minusDays(2);
        editedTask.name = "What";

        boolean editSuccess = taskRepo.updateTask(testUser, editedTask);
        assertThat(editSuccess, equalTo(true));

        List<Task> expectedGetResult2 = new ArrayList<Task>();
        expectedGetResult2.add(editedTask);
        List<Task> actualGetResult2 = taskRepo.getSortedTasks(testUser,
                startDate, endDate).getValue();
        assertThat(actualGetResult2, equalTo(expectedGetResult2));
    }

    /**
     * Test deleting a task for a user.
     */
    @Test
    public void testDeleteTask() {
        List<Task> expectedGetResult = new ArrayList<>();
        int taskAmt = 3;
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(1990, 4, 2);
        LocalDate endDate = startDate.plusDays(1);

        for (int i = 0; i < taskAmt; i++) {
            Task task = new Task();
            task.id = i;
            task.userId = testUser.id;
            task.localDueDate = startDate;
            expectedGetResult.add(task);

            boolean addResult = taskRepo.addTask(testUser, task);
            assertThat(addResult, equalTo(true));
        }

        boolean deleteResult1 = taskRepo.deleteTask(testUser,
                expectedGetResult.get(1));
        assertThat(deleteResult1, equalTo(true));

        expectedGetResult.remove(1);
        Collections.sort(expectedGetResult, new SortByDate());
        List<Task> getResult1 = taskRepo.getSortedTasks(testUser, startDate,
                endDate).getValue();
        assertThat(getResult1, equalTo(expectedGetResult));

        boolean deleteResult2 = taskRepo.deleteTask(testUser,
                expectedGetResult.get(0));
        assertThat(deleteResult2, equalTo(true));

        expectedGetResult.remove(0);
        List<Task> getResult2 = taskRepo.getSortedTasks(testUser, startDate,
                endDate).getValue();
        assertThat(getResult2, equalTo(expectedGetResult));

        boolean deleteResult3 = taskRepo.deleteTask(testUser,
                expectedGetResult.get(0));
        assertThat(deleteResult3, equalTo(true));

        expectedGetResult.remove(0);
        List<Task> getResult3 = taskRepo.getSortedTasks(testUser, startDate,
                endDate).getValue();
        assertThat(getResult3, equalTo(expectedGetResult));
    }

    /**
     * Test editing a nonexistent task for a user.
     */
    @Test
    public void testEditMissingTask() {
        User testUser = new User(1, "test", null);
        Task task = new Task();
        task.id = 1;
        task.userId = testUser.id;
        task.name = "Edited";
        boolean editResult = taskRepo.updateTask(testUser, task);
        assertThat(editResult, equalTo(false));

        boolean addResult = taskRepo.addTask(testUser, task);
        assertThat(addResult, equalTo(true));

        Task task2 = new Task();
        task2.id = 500;
        task.userId = testUser.id;
        task2.name = "None";
        boolean editResult2 = taskRepo.updateTask(testUser, task2);
        assertThat(editResult2, equalTo(false));
    }

    /**
     * Test deleting a missing task for a user.
     */
    @Test
    public void testDeleteMissingTask() {
        User testUser = new User(1, "test", null);
        Task task = new Task();
        boolean deleteResult = taskRepo.deleteTask(testUser, task);
        assertThat(deleteResult, equalTo(false));

        int taskAmt = 5;
        for (int i = 0; i < taskAmt; i++) {
            Task t = new Task();
            t.id = i + 1;
            t.userId = testUser.id;

            boolean addResult = taskRepo.addTask(testUser, t);
            assertThat(addResult, equalTo(true));
        }

        boolean deleteResult2 = taskRepo.deleteTask(testUser, task);
        assertThat(deleteResult2, equalTo(false));
    }

    /**
     * Test adding tasks for different users.
     */
    @Test
    public void testAddForDiffUsers() {
        LocalDate startDate = new LocalDate(2006, 5, 6);
        LocalDate endDate = startDate.plusWeeks(1);

        User testUser1 = new User(1, "test", null);
        Task task1 = new Task();
        task1.id = 1;
        task1.userId = testUser1.id;
        task1.localDueDate = startDate;

        boolean addResult = taskRepo.addTask(testUser1, task1);
        assertThat(addResult, equalTo(true));

        User testUser2 = new User(2, "tester", null);
        Task task2 = new Task();
        task2.id = 2;
        task2.userId = testUser2.id;
        task2.localDueDate = startDate;

        boolean addResult2 = taskRepo.addTask(testUser2, task2);
        assertThat(addResult2, equalTo(true));

        List<Task> expectedGetResult1 = new ArrayList<>();
        expectedGetResult1.add(task1);
        List<Task> getResult1 = taskRepo.getSortedTasks(testUser1, startDate,
                endDate).getValue();
        assertThat(getResult1, equalTo(expectedGetResult1));

        List<Task> expectedGetResult2 = new ArrayList<>();
        expectedGetResult2.add(task2);
        List<Task> getResult2 = taskRepo.getSortedTasks(testUser2, startDate,
                endDate).getValue();
        assertThat(getResult2, equalTo(expectedGetResult2));
    }
}