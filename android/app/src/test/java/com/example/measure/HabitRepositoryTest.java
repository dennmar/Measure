package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestHabitRepositoryComponent;
import com.example.measure.di.components.TestHabitRepositoryComponent;
import com.example.measure.models.data.Habit;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.models.data.User;
import com.example.measure.models.habit.HabitRepository;
import com.example.measure.utils.DBOperationException;
import com.example.measure.utils.InvalidQueryException;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.measure.CustomMatchers.listReflectEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test the habit repository.
 */
public class HabitRepositoryTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    HabitRepository habitRepo;

    /**
     * Create a new habit repository.
     */
    @Before
    public void initHabitRepository() {
        TestHabitRepositoryComponent testComponent =
                DaggerTestHabitRepositoryComponent.create();
        habitRepo = testComponent.habitRepository();
    }

    /**
     * Test retrieving habits when none exist for the user.
     */
    @Test
    public void testGetEmptyHabits() throws DBOperationException {
        User testUser = new User("test", "test@final.com", "password");
        List<Habit> expectedHabits = new ArrayList<>();
        List<Habit> getResult = habitRepo.getHabits(testUser).getValue();
        assertThat(getResult, equalTo(expectedHabits));
    }

    /**
     * Test adding a single habit.
     */
    @Test
    public void testAddHabit()
            throws DBOperationException, InvalidQueryException {
        User testUser = new User("test", "test@final.com", "password");
        Habit habit = new Habit("Stretch", new HashSet<>());
        habit.setUserId(testUser.getId());
        List<Habit> expectedHabits = new ArrayList<>();
        expectedHabits.add(habit);

        habitRepo.addHabit(testUser, habit);
        List<Habit> getResult = habitRepo.getHabits(testUser).getValue();
        assertThat(getResult, equalTo(expectedHabits));
    }

    /**
     * Test adding multiple habits.
     */
    @Test
    public void testAddMultHabits()
            throws DBOperationException, InvalidQueryException {
        User testUser = new User("test", "test@final.com", "password");
        List<Habit> expectedHabits = new ArrayList<>();
        int newHabits = 5;

        for (int i = 0; i < newHabits; i++) {
            HashSet<LocalDate> completions = new HashSet<>();
            Habit habit = new Habit("Karate chop " + i + " times",
                    completions);
            habit.setUserId(testUser.getId());

            habitRepo.addHabit(testUser, habit);
            expectedHabits.add(habit);
        }

        List<Habit> getResult = habitRepo.getHabits(testUser).getValue();
        assertThat(getResult, equalTo(expectedHabits));
    }

    /**
     * Test adding a habit completion to a single habit.
     */
    @Test
    public void testAddHabitCompletion()
            throws DBOperationException, InvalidQueryException {
        User testUser = new User("test", "test@final.com", "password");
        Habit habit = new Habit("Meditate", new HashSet<>());
        habit.setUserId(testUser.getId());
        List<Habit> expectedHabits = new ArrayList<>();
        expectedHabits.add(habit);

        habitRepo.addHabit(testUser, habit);
        List<Habit> getResult = habitRepo.getHabits(testUser).getValue();
        assertThat(getResult, equalTo(expectedHabits));

        Habit habit2 = new Habit(habit.getName(), new HashSet<>());
        habit2.setUserId(testUser.getId());
        habit2.getCompletions().add(LocalDate.now());
        List<Habit> expectedHabits2 = new ArrayList<>();
        expectedHabits2.add(habit2);

        HabitCompletion habitCompletion = new HabitCompletion(habit.getId(),
                LocalDate.now());
        habitRepo.addHabitCompletion(testUser, habit, habitCompletion);
        assertThat(getResult, listReflectEquals(expectedHabits2));
    }

    /**
     * Test adding multiple habit completions.
     */
    @Test
    public void testAddMultHabitCompletions()
            throws DBOperationException, InvalidQueryException {
        User testUser = new User("test", "test@final.com", "password");
        List<Habit> addedHabits = new ArrayList<>();
        List<Habit> expectedHabits = new ArrayList<>();
        int newHabits = 5;

        for (int i = 0; i < newHabits; i++) {
            HashSet<LocalDate> completions = new HashSet<>();
            Habit habit = new Habit("Spar " + i + " times", completions);
            habit.setId(i);
            habit.setUserId(testUser.getId());

            Habit expHabit = new Habit(habit.getName(), new HashSet<>());
            expHabit.setId(i);
            expHabit.setUserId(testUser.getId());

            habitRepo.addHabit(testUser, habit);
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
                    habitRepo.addHabitCompletion(testUser, addedHabits.get(i),
                            habitComp);
                    expectedHabits.get(i).getCompletions()
                            .add(habitComp.getLocalCompletionDate());
                }
                mod++;
            }
        }

        List<Habit> getResult = habitRepo.getHabits(testUser).getValue();
        assertThat(getResult, listReflectEquals(expectedHabits));
    }

    /**
     * Test adding a habit completion to a missing habit.
     */
    @Test
    public void testAddMissingHabitCompletion() throws InvalidQueryException {
        User testUser = new User("test", "test@final.com", "password");
        Habit habit = new Habit("Say hello", new HashSet<>());
        habit.setId(5000);
        habit.setUserId(testUser.getId());
        HabitCompletion habitComp = new HabitCompletion(habit.getId(), LocalDate.now());

        try {
            habitRepo.addHabitCompletion(testUser, habit, habitComp);
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }
    }

    /**
     * Test adding a habit completion to a habit that the user doesn't own.
     */
    @Test
    public void testUnauthAddMissingHabitCompletion()
            throws DBOperationException, InvalidQueryException {
        User testUser = new User("test", "test@final.com", "password");
        Habit habit = new Habit("Go to doctor", new HashSet<>());
        testUser.setId(1);
        habit.setUserId(testUser.getId());

        List<Habit> expectedHabits = new ArrayList<>();
        expectedHabits.add(habit);
        habitRepo.addHabit(testUser, habit);
        List<Habit> getResult = habitRepo.getHabits(testUser).getValue();
        assertThat(getResult, equalTo(expectedHabits));

        User testUser2 = new User("test2", "test2@final.com", "password");
        testUser2.setId(2);
        HabitCompletion habitComp = new HabitCompletion(habit.getId(), LocalDate.now());

        try {
            habitRepo.addHabitCompletion(testUser2, habit, habitComp);
            assertThat(false, equalTo(true));
        }
        catch (InvalidQueryException e) {
            // Expected behavior.
        }
    }
}
