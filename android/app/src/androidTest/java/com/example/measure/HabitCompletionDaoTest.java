package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.RoomDatabase;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.DaggerTestHabitCompletionDaoComponent;
import com.example.measure.di.components.TestHabitCompletionDaoComponent;
import com.example.measure.models.data.Habit;
import com.example.measure.models.data.HabitCompletion;
import com.example.measure.models.data.User;
import com.example.measure.models.habit.HabitCompletionDao;
import com.example.measure.models.habit.HabitDao;
import com.example.measure.utils.DBOperationException;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.measure.CustomMatchers.listReflectEquals;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Run instrumented integration tests on the habit DAO and habit completion DAO
 * (and the Room database).
 */
@RunWith(AndroidJUnit4.class)
public class HabitCompletionDaoTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    HabitDao habitDao;
    HabitCompletionDao habitCompDao;
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
        TestHabitCompletionDaoComponent testHabitCompletionDaoComponent =
                DaggerTestHabitCompletionDaoComponent
                        .factory()
                        .newAppComponent(app);
        habitCompDao = testHabitCompletionDaoComponent.habitCompletionDao();
        habitDao = testHabitCompletionDaoComponent.habitDao();

        // Clear all data before each test.
        testRoomDb = testHabitCompletionDaoComponent.measureRoomDb();
        testRoomDb.clearAllTables();
    }

    /**
     * Test retrieving the completions for a habit without any completions.
     */
    @Test
    public void testGetEmptyCompletions() throws DBOperationException  {
        User testUser = new User("tst", "email@fake.net", "password");
        Habit habit = new Habit("Cut the grass", new HashSet<>());
        habit.setUserId(testUser.getId());
        habit.setId(1);
        habitDao.addHabit(testUser, habit);

        List<Habit> expectedHabits = new ArrayList<>();
        expectedHabits.add(habit);
        List<Habit> getResult = habitDao.getHabits(testUser).getValue();
        assertThat(getResult, listReflectEquals(expectedHabits));
    }

    /**
     * Test retrieving completions for multiple habits with varying amounts of completions.
     */
    @Test
    public void testGetCompletions() throws DBOperationException {
        User testUser = new User("tst", "email@fake.net", "password");
        List<Habit> expectedHabits = new ArrayList<>();
        int numHabits = 5;

        for (int i = 0; i < numHabits; i++) {
            Habit habit = new Habit("Handstand", new HashSet<>());
            habit.setUserId(testUser.getId());
            habit.setId(i + 1);
            habitDao.addHabit(testUser, habit);

            for (int j = 0; j < i; j++) {
                LocalDate compDate = LocalDate.now().minusDays(j);
                HabitCompletion habitComp = new HabitCompletion(habit.getId(),
                        compDate);
                habit.getCompletions().add(compDate);
                habitCompDao.addHabitCompletion(habitComp);
            }

            expectedHabits.add(habit);
        }

        List<Habit> getResult = habitDao.getHabits(testUser).getValue();
        assertThat(getResult, listReflectEquals(expectedHabits));
    }
}
