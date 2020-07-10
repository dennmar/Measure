package com.example.measure;

import android.os.Bundle;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestAgendaViewModelComponent;
import com.example.measure.di.components.TestAgendaViewModelComponent;
import com.example.measure.features.agenda.viewmodel.AgendaViewModel;
import com.example.measure.models.data.Task;
import com.example.measure.utils.SortByDate;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test the agenda view model.
 */
public class AgendaViewModelTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();
    @Mock
    private Bundle mockSavedInstanceState;

    AgendaViewModel avm;

    /**
     * Create a new agenda view model.
     */
    @Before
    public void initAgendaViewModel() {
        // Initialize mocks annotated with @Mock before each test method.
        MockitoAnnotations.initMocks(this);
        
        TestAgendaViewModelComponent testComponent =
                DaggerTestAgendaViewModelComponent.create();
        avm = testComponent.avmFactory().create(mockSavedInstanceState);
    }

    /**
     * Add multiple tasks.
     *
     * @param addAmt       amount of tasks to add
     * @param startingId   id of the first task to add (will be incremented)
     * @param startingDate date of the first task to add (will be incremented)
     * @return list of tasks that were added
     */
    private List<Task> addMultiple(int addAmt, int startingId, LocalDate startingDate) {
        List<Task> addedTasks = new ArrayList<>();

        for (int i = 0; i < addAmt; i++) {
            Task task = new Task();
            task.setId(startingId + i);
            task.setUserId(1);
            task.setName(Integer.toString(task.getId()));
            task.setLocalDueDate(startingDate.plusDays(i));

            addedTasks.add(task);
            boolean addSuccess = avm.addTask(task);
            assertThat(addSuccess, equalTo(true));
        }

        List<Task> getResult = avm.getSortedTasks(startingDate,
                startingDate.plusDays(addAmt)).getValue();
        assertThat(getResult, equalTo(addedTasks));

        return getResult;
    }

    /**
     * Test getting tasks when none exists for the user.
     */
    @Test
    public void testGetEmptyTasks() {
        LocalDate startDate = new LocalDate(1999, 1, 1);
        LocalDate endDate = new LocalDate(2000, 1, 11);

        List<Task> expectedTasks = new ArrayList<>();
        List<Task> getResult = avm.getSortedTasks(startDate, endDate)
                .getValue();
        assertThat(getResult, equalTo(expectedTasks));
    }

    /**
     * Test adding a single task for the user.
     */
    @Test
    public void testAddTask() {
        LocalDate startDate = new LocalDate(2001, 5, 5);
        LocalDate endDate = startDate.plusMonths(6);
        Task task = new Task();
        task.setId(1);
        task.setUserId(1);
        task.setLocalDueDate(new LocalDate(startDate));

        boolean addResult = avm.addTask(task);
        assertThat(addResult, equalTo(true));

        List<Task> expectedGetResult = new ArrayList<>();
        expectedGetResult.add(task);
        List<Task> getResult = avm.getSortedTasks(startDate, endDate)
                .getValue();
        assertThat(getResult, equalTo(expectedGetResult));
    }

    /**
     * Test adding multiple different tasks for a user and check if they are
     * retrieved in sorted order.
     */
    @Test
    public void testGetIsSorted() {
        List<Task> expectedGetResult = new ArrayList<>();
        int taskAmt = 10;
        LocalDate startDate = new LocalDate(2007, 12, 23);
        LocalDate endDate = startDate.plusMonths(10);

        for (int i = taskAmt - 1; i >= 0; i--) {
            Task task = new Task();
            task.setId(taskAmt - i);
            task.setUserId(1);
            task.setLocalDueDate(startDate.plusDays(i));
            expectedGetResult.add(task);

            boolean addResult = avm.addTask(task);
            assertThat(addResult, equalTo(true));
        }

        List<Task> getResult = avm.getSortedTasks(startDate, endDate)
                .getValue();
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
        LocalDate dateOffset = new LocalDate(2014, 2, 2);
        LocalDate startDate = dateOffset.plusDays(taskAmt / 2);
        LocalDate endDate = dateOffset.plusDays(taskAmt - 1);

        for (int i = 0; i < taskAmt; i++) {
            Task task = new Task();
            task.setId(i + 1);
            task.setUserId(1);
            task.setLocalDueDate(dateOffset.plusDays(taskAmt - i - 1));

            if (task.getLocalDueDate().compareTo(startDate) >= 0
                    && task.getLocalDueDate().compareTo(endDate) < 0) {
                expectedGetResult.add(task);
            }

            boolean addResult = avm.addTask(task);
            assertThat(addResult, equalTo(true));
        }

        List<Task> getResult = avm.getSortedTasks(startDate, endDate)
                .getValue();
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
        LocalDate startDate = new LocalDate(2019, 12, 3);
        LocalDate endDate = startDate.plusYears(2);

        addMultiple(taskAmt / 2, 1, endDate);
        addMultiple(taskAmt / 2, 1 + taskAmt / 2, startDate.minusYears(1));
        List<Task> getResult = avm.getSortedTasks(startDate, endDate)
                .getValue();
        assertThat(getResult, equalTo(expectedGetResult));
    }

    /**
     * Test editing a task for the user.
     */
    @Test
    public void testEditTask() {
        LocalDate startDate = new LocalDate(2014, 4, 2);
        LocalDate endDate = startDate.plusMonths(4);

        Task task = new Task();
        task.setId(1);
        task.setUserId(1);
        task.setLocalDueDate(endDate.minusDays(1));
        task.setName("Why");

        boolean addSuccess = avm.addTask(task);
        assertThat(addSuccess, equalTo(true));

        List<Task> expectedGetResult1 = new ArrayList<>();
        expectedGetResult1.add(task);
        List<Task> actualGetResult1 = avm.getSortedTasks(startDate, endDate)
                .getValue();
        assertThat(actualGetResult1, equalTo(expectedGetResult1));

        Task editedTask = new Task();
        editedTask.setId(1);
        editedTask.setUserId(1);
        editedTask.setLocalDueDate(endDate.minusDays(2));
        editedTask.setName("What");

        boolean editSuccess = avm.updateTask(editedTask);
        assertThat(editSuccess, equalTo(true));

        List<Task> expectedGetResult2 = new ArrayList<>();
        expectedGetResult2.add(editedTask);
        List<Task> actualGetResult2 = avm.getSortedTasks(startDate, endDate)
                .getValue();
        assertThat(actualGetResult2, equalTo(expectedGetResult2));
    }

    /**
     * Test deleting a task for a user.
     */
    @Test
    public void testDeleteTask() {
        int taskAmt = 3;
        int[] removeOrder = {1, 0, 0};
        LocalDate startDate = new LocalDate(1990, 4, 2);
        LocalDate endDate = startDate.plusDays(taskAmt);

        List<Task> addedTasks = addMultiple(taskAmt, 1, startDate);
        List<Task> expectedGetResult = addedTasks;

        for (int i = 0; i < taskAmt; i++) {
            boolean deleteSuccess = avm.deleteTask(expectedGetResult
                    .get(removeOrder[i]));
            assertThat(deleteSuccess, equalTo(true));

            expectedGetResult.remove(removeOrder[i]);
            List<Task> getResult = avm.getSortedTasks(startDate, endDate)
                    .getValue();
            assertThat(getResult, equalTo(expectedGetResult));
        }
    }

    /**
     * Test editing a nonexistent task for a user.
     */
    @Test
    public void testEditMissingTask() {
        Task task = new Task();
        task.setId(1);
        task.setUserId(1);
        task.setName("Edited");

        boolean editResult = avm.updateTask(task);
        assertThat(editResult, equalTo(false));

        boolean addResult = avm.addTask(task);
        assertThat(addResult, equalTo(true));

        Task task2 = new Task();
        task2.setId(0);
        task2.setUserId(1);
        task2.setName("None");

        boolean editResult2 = avm.updateTask(task2);
        assertThat(editResult2, equalTo(false));
    }

    /**
     * Test deleting a missing task for a user.
     */
    @Test
    public void testDeleteMissingTask() {
        Task task = new Task();
        boolean deleteResult = avm.deleteTask(task);
        assertThat(deleteResult, equalTo(false));

        int taskAmt = 5;
        for (int i = 0; i < taskAmt; i++) {
            Task t = new Task();
            t.setId(i + 1);
            t.setUserId(1);

            boolean addResult = avm.addTask(t);
            assertThat(addResult, equalTo(true));
        }

        boolean deleteResult2 = avm.deleteTask(task);
        assertThat(deleteResult2, equalTo(false));
    }

    /**
     * Test setting the initial active task.
     */
    @Test
    public void testInitialSetActiveTask() {
        Task task = new Task();
        task.setLocalDueDate(new LocalDate(2020, 4, 17));
        boolean setResult = avm.updateActiveTask(task);
        assertThat(setResult, equalTo(true));

        Task getResult = avm.getActiveTask().getValue();
        assertThat(getResult, equalTo(task));
    }

    /**
     * Test switching the active task.
     */
    @Test
    public void testSwapActiveTask() {
        Task t1 = new Task();
        t1.setLocalDueDate(new LocalDate(1988, 9, 19));
        boolean setResult1 = avm.updateActiveTask(t1);
        assertThat(setResult1, equalTo(true));

        Task getResult1 = avm.getActiveTask().getValue();
        assertThat(getResult1, equalTo(t1));

        Task t2 = new Task();
        t2.setLocalDueDate(new LocalDate(2000, 3, 2));
        boolean setResult2 = avm.updateActiveTask(t2);
        assertThat(setResult2, equalTo(true));

        Task getResult2 = avm.getActiveTask().getValue();
        assertThat(getResult2, equalTo(t2));
    }

    /**
     * Test getting the active task for the user when none is set.
     */
    @Test
    public void testMissingActiveTask() {
        Task getResult = avm.getActiveTask().getValue();
        assertThat(getResult, equalTo(null));
    }
}