package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.RoomDatabase;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.DaggerTestTaskDaoComponent;
import com.example.measure.di.components.TestTaskDaoComponent;
import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.models.task.SortByDate;
import com.example.measure.models.task.TaskDao;
import com.example.measure.utils.DBOperationException;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.measure.LooseMatch.looseMatch;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Run integration tests on the task DAO (and the Room database).
 *
 * Note: Unit testing resulted in java.lang.RuntimeException: Method
 * getMainLooper in android.os.Looper not mocked.
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTaskDaoTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    MeasureApplication mockApp;
    TaskDao taskDao;
    RoomDatabase testRoomDb;

    /**
     * Create a new task DAO.
     */
    @Before
    public void initTaskDao() {
        mockApp = (MeasureApplication) InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext()
                .getApplicationContext();
        TestTaskDaoComponent taskDaoComponent =
                DaggerTestTaskDaoComponent.factory().newAppComponent(mockApp);
        taskDao = taskDaoComponent.taskDao();

        // Clear all data before each test.
        testRoomDb = taskDaoComponent.measureRoomDatabase();
        testRoomDb.clearAllTables();
    }

    @After
    public void closeConnections() {
        testRoomDb.close();
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
                                   LocalDate startingDate)
                                   throws DBOperationException {
        List<Task> addedTasks = new ArrayList<>();

        for (int i = 0; i < addAmt; i++) {
            Task task = new Task();
            task.id = startingId + i;
            task.userId = taskOwner.id;
            task.name = Integer.toString(task.id);
            task.localDueDate = startingDate.plusDays(i);

            taskDao.addTask(taskOwner, task);
            addedTasks.add(task);
        }

        List<Task> getResult = taskDao.getSortedTasks(taskOwner, startingDate,
                startingDate.plusDays(addAmt)).getValue();
        assertThat(getResult, looseMatch(addedTasks));

        return getResult;
    }

    /**
     * Test getting tasks when none exists for the user.
     */
    @Test
    public void testGetEmptyTasks() {
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(1990, 11, 27);
        LocalDate endDate = startDate.plusYears(30);

        List<Task> expectedTasks = new ArrayList<>();
        try {
            List<Task> getResult = taskDao.getSortedTasks(testUser, startDate,
                    endDate).getValue();
            assertThat(getResult, looseMatch(expectedTasks));
        }
        catch (DBOperationException e){
            assertThat(false, equalTo(true));
        }
    }

    /**
     * Test getting a subset of a user's task within a date range.
     *
     * @throws DBOperationException if a task could not be added
     */
    @Test
    public void testGetSubset() throws DBOperationException {
        List<Task> expectedGetResult = new ArrayList<>();
        int taskAmt = 10;
        User testUser = new User(1, "test", null);
        LocalDate dateOffset = new LocalDate(1995, 5, 22);
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

            taskDao.addTask(testUser, task);
        }

        List<Task> getResult = taskDao.getSortedTasks(testUser, startDate,
                endDate).getValue();
        Collections.sort(expectedGetResult, new SortByDate());
        assertThat(getResult, looseMatch(expectedGetResult));
    }

    /**
     * Test getting a date range with no tasks.
     *
     * @throws DBOperationException if a task could not be added
     */
    @Test
    public void testGetNoMatchingTasks() throws DBOperationException {
        User testUser = new User(1, "test", null);
        LocalDate addStartDate = new LocalDate(1960, 7, 1);
        LocalDate addEndDate = addStartDate.plusWeeks(2);
        LocalDate queryStartDate = addEndDate;
        LocalDate queryEndDate = queryStartDate.plusMonths(2);
        List<Task> expectedGetResult = new ArrayList<>();

        addMultiple(10, 1, testUser, addStartDate);
        List<Task> getResultNoMatch = taskDao.getSortedTasks(testUser,
                queryStartDate, queryEndDate).getValue();
        assertThat(getResultNoMatch, looseMatch(expectedGetResult));
    }

    /**
     * Test editing a task for a user.
     *
     * @throws DBOperationException if a task could not be added or edited
     */
    @Test
    public void testEditTask() throws DBOperationException {
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(1982, 8, 2);
        LocalDate endDate = startDate.plusDays(5);

        LocalDate taskDate = endDate.minusDays(1);
        Task task = new Task(1, testUser.id, "Why", null, taskDate, false);
        List<Task> expectedGetResult1 = new ArrayList<>();
        expectedGetResult1.add(task);

        taskDao.addTask(testUser, task);
        List<Task> actualGetResult1 = taskDao.getSortedTasks(testUser,
                startDate, endDate).getValue();
        assertThat(actualGetResult1, looseMatch(expectedGetResult1));

        int actualTaskId = actualGetResult1.get(0).id;
        Task editedTask = new Task(actualTaskId, testUser.id, "What", null, taskDate,
                true);
        List<Task> expectedGetResult2 = new ArrayList<Task>();
        expectedGetResult2.add(editedTask);

        taskDao.updateTask(testUser, editedTask);
        List<Task> actualGetResult2 = taskDao.getSortedTasks(testUser,
                startDate, endDate).getValue();
        assertThat(actualGetResult2, looseMatch(expectedGetResult2));
    }

    /**
     * Test deleting a task for a user.
     *
     * @throws DBOperationException if a task could not be added or deleted
     */
    @Test
    public void testDeleteTask() throws DBOperationException {
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(2054, 1, 5);
        LocalDate endDate = startDate.plusMonths(20);

        int[] removeOrder = {1, 0, 0};
        int taskAmt = 3;

        List<Task> addedTasks = addMultiple(taskAmt, 1, testUser, startDate);
        List<Task> expectedGetResult = addedTasks;

        for (int i = 0; i < taskAmt; i++) {
            taskDao.deleteTask(testUser,
                    expectedGetResult.get(removeOrder[i]));
            expectedGetResult.remove(removeOrder[i]);
            List<Task> getResult = taskDao.getSortedTasks(testUser, startDate,
                    endDate).getValue();
            assertThat(getResult, looseMatch(expectedGetResult));
        }
    }

    /**
     * Test editing a nonexistent task for a user.
     *
     * @throws DBOperationException if a task could not be added
     */
    @Test
    public void testEditMissingTask() throws DBOperationException {
        User testUser = new User(1, "test", null);
        Task task = new Task();
        task.id = 1;
        task.userId = testUser.id;
        task.name = "Edited";

        try {
            taskDao.updateTask(testUser, task);
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e){
            // Expected behavior.
        }

        taskDao.addTask(testUser, task);

        Task task2 = new Task();
        task2.id = 500;
        task.userId = testUser.id;
        task2.name = "None";

        try {
            taskDao.updateTask(testUser, task2);
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }
    }

    /**
     * Test deleting a missing task for a user.
     *
     * @throws DBOperationException if a task could not be added
     */
    @Test
    public void testDeleteMissingTask() throws DBOperationException {
        List<Task> expectedGetResult = new ArrayList<>();
        int taskAmt = 5;
        User testUser = new User(1, "test", null);
        LocalDate startDate = new LocalDate(2030, 2, 1);
        LocalDate endDate = startDate.plusDays(taskAmt);

        Task task = new Task();
        task.id = 0;
        task.userId = testUser.id;
        task.localDueDate = startDate;

        try {
            taskDao.deleteTask(testUser, task);
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }

        for (int i = 0; i < taskAmt; i++) {
            Task t = new Task();
            t.id = i + 1;
            t.userId = testUser.id;
            t.localDueDate = startDate;
            taskDao.addTask(testUser, t);
            expectedGetResult.add(t);
        }

        List<Task> getResult = taskDao.getSortedTasks(testUser, startDate,
                endDate).getValue();
        assertThat(getResult, looseMatch(expectedGetResult));

        try {
            taskDao.deleteTask(testUser, task);
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }
    }

    /**
     * Test editing a task belonging to another user.
     *
     * @throws DBOperationException if a task could not be added
     */
    @Test
    public void testEditOtherTask() throws DBOperationException {
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
            taskDao.updateTask(testUser1, addedTasks2.get(0));
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }

        try {
            taskDao.updateTask(testUser2, addedTasks1.get(taskAmt1 - 1));
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
    public void testDeleteOtherTask() throws DBOperationException {
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
            taskDao.deleteTask(testUser1, addedTasks2.get(taskAmt2 - 1));
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }

        try {
            taskDao.deleteTask(testUser2, addedTasks1.get(0));
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }
    }
}
