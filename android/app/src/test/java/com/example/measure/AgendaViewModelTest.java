package com.example.measure;

import android.os.Bundle;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestAgendaViewModelComponent;
import com.example.measure.di.components.TestAgendaViewModelComponent;
import com.example.measure.features.agenda.AgendaViewModel;
import com.example.measure.models.data.Task;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AgendaViewModelTest {
    // Initialize mocks annotated with @Mock before each test method.
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule()
            .strictness(Strictness.STRICT_STUBS);
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
        TestAgendaViewModelComponent testComponent =
                DaggerTestAgendaViewModelComponent.create();
        avm = testComponent.avmFactory().create(mockSavedInstanceState);
    }

    /**
     * Sort the tasks by date in ascending order.
     */
    public class SortByDate implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.localDueDate.compareTo(o2.localDueDate);
        }
    }

    /**
     * Test getting tasks when none exists for the user.
     */
    @Test
    public void testGetEmptyTasks() {
        Date startDate = new Date(10000);
        Date endDate = new Date(50000);

        List<Task> expectedResult = new ArrayList<Task>();
        List<Task> actualResult = avm.getSortedTasks(startDate, endDate)
                .getValue();
        assertThat(actualResult, equalTo(expectedResult));
    }

    /**
     * Test adding a single task for a user.
     */
    @Test
    public void testAddTask() {
        Date startDate = new Date(12938902);
        Date endDate = new Date(13000000);
        Task task = new Task();
        task.localDueDate = startDate;

        List<Task> expectedGetResult = new ArrayList<Task>();
        expectedGetResult.add(task);

        boolean addResult = avm.addTask(task);
        assertThat(addResult, equalTo(true));

        List<Task> getResult = avm.getSortedTasks(startDate, endDate)
                .getValue();
        assertThat(getResult, equalTo(expectedGetResult));
    }

    /**
     * Test adding multiple different tasks and retrieving them.
     */
    @Test
    public void testGetMultipleDiffTasks() {
        List<Task> expectedGetResult = new ArrayList<>();
        int taskAmt = 10;
        Date startDate = new Date(100);
        Date endDate = new Date(startDate.getTime() + taskAmt);

        for (int i = taskAmt - 1; i >= 0; i--) {
            Task task = new Task();
            task.id = i;
            task.localDueDate = new Date(startDate.getTime() + i);
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
        int timeOffset = 100;
        Date startDate = new Date(timeOffset + (taskAmt / 2));
        Date endDate = new Date(timeOffset + taskAmt - 1);

        for (int i = 0; i < taskAmt; i++) {
            Task task = new Task();
            task.id = i + 1;
            task.localDueDate = new Date(timeOffset + taskAmt - i - 1);

            if (task.localDueDate.equals(startDate)
                    || (task.localDueDate.after(startDate)
                    && task.localDueDate.before(endDate))) {
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
        Date startDate = new Date(1000);
        Date endDate = new Date(2000);

        for (int i = taskAmt; i >= 0; i--) {
            Task task = new Task();
            task.id = taskAmt - i + 1;
            task.localDueDate = new Date(i);

            boolean addResult = avm.addTask(task);
            assertThat(addResult, equalTo(true));
        }

        List<Task> getResult = avm.getSortedTasks(startDate, endDate)
                .getValue();
        assertThat(getResult, equalTo(expectedGetResult));
    }

    /**
     * Test editing a task for a user.
     */
    @Test
    public void testEditTask() {
        Date startDate = new Date(12353);
        Date endDate = new Date(112390);
        Task task = new Task();
        task.id = 1;
        task.localDueDate = new Date(endDate.getTime() - 1);
        task.name = "Why";

        boolean addSuccess = avm.addTask(task);
        assertThat(addSuccess, equalTo(true));

        List<Task> expectedGetResult1 = new ArrayList<>();
        expectedGetResult1.add(task);
        List<Task> actualGetResult1 = avm.getSortedTasks(startDate, endDate)
                .getValue();
        assertThat(actualGetResult1, equalTo(expectedGetResult1));

        Task editedTask = new Task();
        editedTask.id = 1;
        editedTask.localDueDate = new Date(endDate.getTime() - 1);
        editedTask.name = "What";

        boolean editSuccess = avm.updateTask(editedTask);
        assertThat(editSuccess, equalTo(true));

        List<Task> expectedGetResult2 = new ArrayList<Task>();
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
        List<Task> expectedGetResult = new ArrayList<>();
        int taskAmt = 3;
        Date startDate = new Date(1000);
        Date endDate = new Date(2000);

        for (int i = 0; i < taskAmt; i++) {
            Task task = new Task();
            task.id = i;
            task.localDueDate = new Date(startDate.getTime());
            expectedGetResult.add(task);

            boolean addResult = avm.addTask(task);
            assertThat(addResult, equalTo(true));
        }

        boolean deleteResult1 = avm.deleteTask(expectedGetResult.get(1));
        assertThat(deleteResult1, equalTo(true));

        expectedGetResult.remove(1);
        Collections.sort(expectedGetResult, new SortByDate());
        List<Task> getResult1 = avm.getSortedTasks(startDate, endDate).getValue();
        assertThat(getResult1, equalTo(expectedGetResult));

        boolean deleteResult2 = avm.deleteTask(expectedGetResult.get(0));
        assertThat(deleteResult2, equalTo(true));

        expectedGetResult.remove(0);
        List<Task> getResult2 = avm.getSortedTasks(startDate, endDate).getValue();
        assertThat(getResult2, equalTo(expectedGetResult));

        boolean deleteResult3 = avm.deleteTask(expectedGetResult.get(0));
        assertThat(deleteResult3, equalTo(true));

        expectedGetResult.remove(0);
        List<Task> getResult3 = avm.getSortedTasks(startDate, endDate).getValue();
        assertThat(getResult3, equalTo(expectedGetResult));
    }

    /**
     * Test editing a nonexistent task for a user.
     */
    @Test
    public void testEditMissingTask() {
        Task task = new Task();
        task.id = 1;
        task.name = "Edited";
        boolean editResult = avm.updateTask(task);
        assertThat(editResult, equalTo(false));

        boolean addResult = avm.addTask(task);
        assertThat(addResult, equalTo(true));

        Task task2 = new Task();
        task2.id = 500;
        task2.name = "None";
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
            t.id = i;

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
        task.localDueDate = new Date(500);
        boolean setResult = avm.updateActiveTask(task);
        assertThat(setResult, equalTo(true));

        Task getResult = avm.getActiveTask();
        assertThat(getResult, equalTo(task));
    }

    /**
     * Test switching the active task.
     */
    @Test
    public void testSwapActiveTask() {
        Task t1 = new Task();
        t1.localDueDate = new Date(1500);
        boolean setResult1 = avm.updateActiveTask(t1);
        assertThat(setResult1, equalTo(true));

        Task getResult1 = avm.getActiveTask();
        assertThat(getResult1, equalTo(t1));

        Task t2 = new Task();
        t2.localDueDate = new Date(2000);
        boolean setResult2 = avm.updateActiveTask(t2);
        assertThat(setResult2, equalTo(true));

        Task getResult2 = avm.getActiveTask();
        assertThat(getResult2, equalTo(t2));
    }

    /**
     * Test getting the active task for the user when none is set.
     */
    @Test
    public void testMissingActiveTask() {
        Task getResult = avm.getActiveTask();
        assertThat(getResult, equalTo(null));
    }
}