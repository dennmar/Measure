package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.RoomDatabase;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.DaggerTestHabitDaoComponent;
import com.example.measure.di.components.TestHabitDaoComponent;
import com.example.measure.models.data.Habit;
import com.example.measure.models.data.User;
import com.example.measure.models.habit.HabitDao;
import com.example.measure.utils.DBOperationException;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.measure.LooseHabitsMatch.looseHabitsMatch;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A component targeted for habit DAO tests that makes Dagger create a graph of
 * dependencies and exposes objects from the application graph.
 */
@RunWith(AndroidJUnit4.class)
public class HabitDaoTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    HabitDao habitDao;
    RoomDatabase testRoomDb;
    MeasureApplication app = (MeasureApplication) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

    /**
     * Create a new habit DAO.
     */
    @Before
    public void initHabitDao() {
        TestHabitDaoComponent habitDaoComponent =
                DaggerTestHabitDaoComponent.factory().newAppComponent(app);
        habitDao = habitDaoComponent.habitDao();

        // Clear all data before each test.
        testRoomDb = habitDaoComponent.measureRoomDb();
        testRoomDb.clearAllTables();
    }

    @After
    public void closeConnections() {
        testRoomDb.close();
    }

    /**
     * Test retrieving habits when none exist for a user.
     */
    @Test
    public void testGetEmptyHabits() throws DBOperationException  {
        User testUser = new User("test", "tester@ertest.com", "password");
        List<Habit> expectedHabits = new ArrayList<>();
        List<Habit> getResult = habitDao.getHabits(testUser).getValue();
        assertThat(getResult, equalTo(expectedHabits));
    }

    /**
     * Test adding a single habit.
     */
    @Test
    public void testAddHabit() throws DBOperationException {
        User testUser = new User("test", "tester@ertest.com", "password");
        Habit habit = new Habit("Check email", new HashSet<>());
        habit.setUserId(testUser.getId());

        List<Habit> expectedHabits = new ArrayList<>();
        expectedHabits.add(habit);
        habitDao.addHabit(testUser, habit);

        List<Habit> getResult = habitDao.getHabits(testUser).getValue();
        assertThat(getResult, looseHabitsMatch(expectedHabits));
    }

    /**
     * Test adding multiple habits.
     */
    @Test
    public void testAddMultHabits() throws DBOperationException {
        User testUser = new User("test", "tester@ertest.com", "password");
        List<Habit> expectedHabits = new ArrayList<>();
        int newHabits = 5;

        for (int i = 1; i <= newHabits; i++) {
            HashSet<LocalDate> completions = new HashSet<>();

            if (i % 2 == 0) {
                completions.add(LocalDate.now());
            }
            if (i % 3 == 0) {
                completions.add(LocalDate.now().minusDays(3));
            }
            if (i % 4 == 0) {
                completions.add(LocalDate.now().minusDays(5));
            }

            Habit habit = new Habit("Chop " + i + " onions",
                    completions);
            habit.setUserId(testUser.getId());
            expectedHabits.add(habit);
            habitDao.addHabit(testUser, habit);
        }

        List<Habit> getResult = habitDao.getHabits(testUser).getValue();
        assertThat(getResult, looseHabitsMatch(expectedHabits));
    }
}
