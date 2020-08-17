package com.example.measure;

import com.example.measure.models.data.Habit;
import com.example.measure.models.data.User;
import com.example.measure.models.habit.HabitRepository;
import com.example.measure.utils.DBOperationException;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test the habit repository.
 */
public class HabitRepositioryTest {
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
    public void testAddHabit() throws DBOperationException {
        User testUser = new User("test", "test@final.com", "password");
        Habit habit = new Habit("Stretch", new HashSet<>());
        habit.setUserId(testUser.getId());

        List<Habit> expectedHabits = new ArrayList<>();
        expectedHabits.add(habit);
        List<Habit> getResult = habitRepo.getHabits(testUser).getValue();
        assertThat(getResult, equalTo(expectedHabits));
    }

    /**
     * Test adding multiple habits.
     */
    @Test
    public void testAddMultHabits() throws DBOperationException {
        User testUser = new User("test", "test@final.com", "password");
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

            Habit habit = new Habit("Karate chop " + i + " times",
                    completions);
            expectedHabits.add(habit);
            habitRepo.addHabit(testUser, habit);
        }

        List<Habit> getResult = habitRepo.getHabits(testUser).getValue();
        assertThat(getResult, equalTo(expectedHabits));
    }
}
