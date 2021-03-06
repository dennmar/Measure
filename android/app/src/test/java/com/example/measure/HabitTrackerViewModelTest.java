package com.example.measure;

import android.os.Bundle;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestHabitTrackerViewModelComponent;
import com.example.measure.di.components.TestHabitTrackerViewModelComponent;
import com.example.measure.features.habit_tracker.HabitTrackerViewModel;
import com.example.measure.models.data.Habit;
import com.example.measure.utils.DBOperationException;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test the habit tracker view model.
 */
public class HabitTrackerViewModelTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();
    @Mock
    private Bundle mockSavedInstanceState;

    HabitTrackerViewModel htvm;

    /**
     * Create a new habit tracker view model.
     */
    @Before
    public void initHabitTrackerViewModel() {
        // Initialize mocks annotated with @Mock before each test method.
        MockitoAnnotations.initMocks(this);

        TestHabitTrackerViewModelComponent testComponent =
                DaggerTestHabitTrackerViewModelComponent.create();
        htvm = testComponent.htvmFactory().create(mockSavedInstanceState);
    }

    /**
     * Test getting habits when none exist for the user.
     */
    @Test
    public void testGetEmptyHabits() throws DBOperationException {
        List<Habit> expectedHabits = new ArrayList<>();
        List<Habit> getResult = htvm.getHabits().getValue();
        assertThat(getResult, equalTo(expectedHabits));
    }

    /**
     * Test adding a single habit.
     */
    @Test
    public void testAddHabit() throws DBOperationException {
        Habit habit = new Habit("Meditate", new HashSet<>());
        habit.setUserId(1);
        List<Habit> expectedHabits = new ArrayList<>();
        expectedHabits.add(habit);

        htvm.addHabit(habit);
        List<Habit> getResult = htvm.getHabits().getValue();
        assertThat(getResult, equalTo(expectedHabits));
    }

    /**
     * Test adding multiple habits.
     */
    @Test
    public void testAddMultHabits() throws DBOperationException {
        List<Habit> expectedHabits = new ArrayList<>();
        int newHabits = 5;

        for (int i = 0; i < newHabits; i++) {
            HashSet<LocalDate> completions = new HashSet<>();
            Habit habit = new Habit("Jump " + i + " times", completions);
            habit.setUserId(1);

            if (i % 2 == 0) {
                completions.add(LocalDate.now());
            }
            if (i % 3 == 0) {
                completions.add(LocalDate.now().minusDays(1));
            }
            if (i % 4 == 0) {
                completions.add(LocalDate.now().minusDays(2));
            }

            htvm.addHabit(habit);
            expectedHabits.add(habit);
        }

        List<Habit> getResult = htvm.getHabits().getValue();
        assertThat(getResult, equalTo(expectedHabits));
    }
}
