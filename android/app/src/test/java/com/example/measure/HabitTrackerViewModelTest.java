package com.example.measure;

import android.os.Bundle;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestHabitTrackerViewModelComponent;
import com.example.measure.di.components.TestHabitTrackerViewModelComponent;
import com.example.measure.features.habit_tracker.HabitTrackerViewModel;
import com.example.measure.models.data.Habit;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.utils.DBOperationException;
import com.example.measure.utils.InvalidQueryException;

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

import static com.example.measure.CustomMatchers.listReflectEquals;
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

            htvm.addHabit(habit);
            expectedHabits.add(habit);
        }

        List<Habit> getResult = htvm.getHabits().getValue();
        assertThat(getResult, equalTo(expectedHabits));
    }

    /**
     * Test adding a habit completion to a single habit.
     */
    @Test
    public void testAddHabitCompletion()
            throws DBOperationException, InvalidQueryException {
        Habit habit = new Habit("Meditate", new HashSet<>());
        habit.setUserId(1);
        List<Habit> expectedHabits = new ArrayList<>();
        expectedHabits.add(habit);

        htvm.addHabit(habit);
        List<Habit> getResult = htvm.getHabits().getValue();
        assertThat(getResult, equalTo(expectedHabits));

        Habit habit2 = new Habit(habit.getName(), new HashSet<>());
        habit2.setUserId(1);
        habit2.getCompletions().add(LocalDate.now());
        List<Habit> expectedHabits2 = new ArrayList<>();
        expectedHabits2.add(habit2);

        HabitCompletion habitCompletion = new HabitCompletion(habit.getId(),
                LocalDate.now());
        htvm.addHabitCompletion(habit, habitCompletion);
        assertThat(getResult, listReflectEquals(expectedHabits2));
    }

    /**
     * Test adding multiple habit completions.
     */
    @Test
    public void testAddMultHabitCompletions()
            throws DBOperationException, InvalidQueryException {
        List<Habit> addedHabits = new ArrayList<>();
        List<Habit> expectedHabits = new ArrayList<>();
        int newHabits = 5;

        for (int i = 0; i < newHabits; i++) {
            HashSet<LocalDate> completions = new HashSet<>();
            Habit habit = new Habit("Jump " + i + " times", completions);
            habit.setUserId(1);
            Habit expHabit = new Habit(habit.getName(), new HashSet<>());
            expHabit.setUserId(1);

            htvm.addHabit(habit);
            addedHabits.add(habit);
            expectedHabits.add(expHabit);
        }

        for (int i = 0; i < newHabits; i++) {
            int mod = 2;

            // Add a variety of habit completions based on the index.
            while (mod <= 4) {
                if (i % mod == 0) {
                    HabitCompletion habitComp = new HabitCompletion(
                            addedHabits.get(i).getId(),
                            LocalDate.now().minusDays(mod - 2)
                    );
                    htvm.addHabitCompletion(addedHabits.get(i), habitComp);
                    expectedHabits.get(i).getCompletions()
                            .add(habitComp.getLocalCompletionDate());
                }
                mod++;
            }
        }

        List<Habit> getResult = htvm.getHabits().getValue();
        assertThat(getResult, listReflectEquals(expectedHabits));
    }
}
