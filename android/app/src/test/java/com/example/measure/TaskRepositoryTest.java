package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestTaskRepositoryComponent;
import com.example.measure.di.components.TestTaskRepositoryComponent;
import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.models.task.SortByDate;
import com.example.measure.models.task.TaskRepository;
import com.example.measure.utils.DBOperationException;
import com.example.measure.utils.InvalidQueryException;

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
     * Add multiple tasks to the database.
     *
     * @param addAmt       amount of tasks to add
     * @param startingId   id of the first task to add (will be incremented)
     * @param taskOwner    user who will own the tasks
     * @param startingDate date of the first task to add (will be incremented)
     * @throws DBOperationException if a task could not be added
     * @return list of tasks that were added
     */
    private List<Task> addMultiple(int addAmt, int startingId, User taskOwner,
            LocalDate startingDate) throws DBOperationException,
            InvalidQueryException {
        List<Task> addedTasks = new ArrayList<>();

        for (int i = 0; i < addAmt; i++) {
            Task task = new Task();
            task.id = startingId + i;
            task.userId = taskOwner.id;
            task.name = Integer.toString(task.id);
            task.localDueDate = startingDate.plusDays(i);

            addedTasks.add(task);
            boolean addSuccess = taskRepo.addTask(taskOwner, task);
            assertThat(addSuccess, equalTo(true));
        }

        List<Task> getResult = taskRepo.getSortedTasks(taskOwner, startingDate,
                startingDate.plusDays(addAmt)).getValue();
        assertThat(getResult, equalTo(addedTasks));

        return getResult;
    }

    /**
     * Test getting tasks when none exists for the user.
     */
    @Test
    public void testGetEmptyTasks() throws DBOperationException,
            InvalidQueryException {
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
    public void testAddTask() throws DBOperationException,
            InvalidQueryException {
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
     * Test adding multiple different tasks for a user and check if they are
     * retrieved in sorted order.
     */
    @Test
    public void testGetIsSorted() throws DBOperationException,
            InvalidQueryException {
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
    public void testGetSubset() throws DBOperationException,
            InvalidQueryException {
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
    public void testGetNoMatchingTasks() throws DBOperationException,
            InvalidQueryException {
        List<Task> expectedGetResult = new ArrayList<>();
        int taskAmt = 10;
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(2019, 12, 3);
        LocalDate endDate = startDate.plusYears(2);

        addMultiple(taskAmt / 2, 1, testUser, endDate);
        addMultiple(taskAmt / 2, 1 + taskAmt / 2, testUser,
                startDate.minusYears(1));
        List<Task> getResult = taskRepo.getSortedTasks(testUser, startDate,
                endDate).getValue();
        assertThat(getResult, equalTo(expectedGetResult));
    }

    /**
     * Test editing a task for a user.
     */
    @Test
    public void testEditTask() throws DBOperationException,
            InvalidQueryException {
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
    public void testDeleteTask() throws DBOperationException,
            InvalidQueryException {
        int taskAmt = 3;
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(1990, 4, 2);
        LocalDate endDate = startDate.plusDays(taskAmt);

        List<Task> addedTasks = addMultiple(taskAmt, 1, testUser, startDate);
        List<Task> expectedGetResult = addedTasks;

        boolean deleteResult1 = taskRepo.deleteTask(testUser,
                expectedGetResult.get(1));
        assertThat(deleteResult1, equalTo(true));

        expectedGetResult.remove(1);
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
    public void testEditMissingTask() throws DBOperationException,
            InvalidQueryException {
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
    public void testDeleteMissingTask() throws DBOperationException,
            InvalidQueryException {
        User testUser = new User(1, "test", null);
        Task task = new Task();
        task.id = 0;
        task.userId = testUser.id;

        boolean deleteResult = taskRepo.deleteTask(testUser, task);
        assertThat(deleteResult, equalTo(false));

        int taskAmt = 5;
        LocalDate startDate = new LocalDate(2003, 3, 1);
        List<Task> addedTasks = addMultiple(taskAmt, 1, testUser, startDate);

        boolean deleteResult2 = taskRepo.deleteTask(testUser, task);
        assertThat(deleteResult2, equalTo(false));
    }

    /**
     * Test adding tasks for different users.
     */
    @Test
    public void testAddForDiffUsers() throws DBOperationException,
            InvalidQueryException {
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

    /**
     * Test editing a task belonging to another user.
     *
     * @throws DBOperationException if a task could not be added
     */
    @Test
    public void testEditOtherTask() throws DBOperationException,
            InvalidQueryException {
        User testUser1 = new User(1, "test", null);
        User testUser2 = new User(2, "Goose", null);
        int taskAmt1 = 3;
        int taskAmt2 = taskAmt1;
        LocalDate startDate = new LocalDate(2031, 4, 26);
        LocalDate endDate = startDate.plusDays(taskAmt1);

        List<Task> addedTasks1 = addMultiple(taskAmt1, 1, testUser1,
                startDate);
        List<Task> addedTasks2 = addMultiple(taskAmt2, taskAmt1 + 1, testUser2,
                startDate);

        try {
            taskRepo.updateTask(testUser1, addedTasks2.get(0));
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }

        try {
            taskRepo.updateTask(testUser2, addedTasks1.get(taskAmt1 - 1));
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }
    }

    /**
     * Test deleting a task belonging to another user.
     *
     * @throws DBOperationException if a task could not be added
     */
    @Test
    public void testDeleteOtherTask() throws DBOperationException,
            InvalidQueryException {
        User testUser1 = new User(1, "test", null);
        User testUser2 = new User(2, "Goose", null);
        int taskAmt1 = 3;
        int taskAmt2 = taskAmt1;
        LocalDate startDate = new LocalDate(2031, 4, 26);
        LocalDate endDate = startDate.plusDays(taskAmt1);

        List<Task> addedTasks1 = addMultiple(taskAmt1, 1, testUser1,
                startDate);
        List<Task> addedTasks2 = addMultiple(taskAmt2, taskAmt1 + 1, testUser2,
                startDate);

        try {
            taskRepo.deleteTask(testUser1, addedTasks2.get(taskAmt2 - 1));
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }

        try {
            taskRepo.deleteTask(testUser2, addedTasks1.get(0));
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }
    }

    /**
     * Test getting tasks with invalid search dates.
     */
    @Test
    public void testInvalidDates() throws DBOperationException {
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(1830, 3, 2);
        LocalDate endDate = startDate.minusDays(1);

        try {
            List<Task> getResult = taskRepo.getSortedTasks(testUser, startDate,
                    endDate).getValue();
            assertThat(false, equalTo(true));
        }
        catch (InvalidQueryException e) {
            // Expected behavior.
        }
    }
}