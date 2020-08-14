package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.ApplicationComponent;
import com.example.measure.di.components.DaggerTestAgendaFragmentComponent;
import com.example.measure.features.MeasureActivity;
import com.example.measure.features.habit_tracker.HabitTrackerFragment;
import com.example.measure.models.data.Habit;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestWatcher;

import java.text.DateFormatSymbols;
import java.util.HashSet;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.measure.CustomMatchers.rowAtPos;

/**
 * Run instrumented integration tests with the measure activity and habit
 * tracker fragment.
 */
public class HabitTrackerFragmentTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    public TestWatcher instantTaskExecutorRule = new InstantTaskExecutorRule();
    public ActivityTestRule<MeasureActivity> measureActivityTestRule =
            new ActivityTestRule<>(MeasureActivity.class);

    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(instantTaskExecutorRule)
            .around(measureActivityTestRule);

    MeasureActivity measureActivity;
    HabitTrackerFragment habitTrackerFrag;
    MeasureApplication app = (MeasureApplication) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

    /**
     * Display the agenda fragment.
     */
    @Before
    public void initAgendaFragment() {
        ApplicationComponent testComponent = DaggerTestAgendaFragmentComponent
                .factory()
                .newAppComponent(app);
        app.setAppComponent(testComponent);
        measureActivity = measureActivityTestRule.getActivity();
        habitTrackerFrag = new HabitTrackerFragment();
        measureActivity.replaceFragment(habitTrackerFrag);
    }

    /**
     * Add a habit to the habit tracker.
     *
     * @param habit       habit to be added
     * @param expectedPos expected position in the habits recycler view
     */
    private void addHabit(Habit habit, int expectedPos) {
        // Open habit creation dialog from agenda screen.
        onView(withId(R.id.btn_add_habit)).perform(click());

        // Perform actions and checks on habit creation dialog.
        onView(withId(R.id.edittext_new_habit_name))
                .perform(typeText(habit.getName()));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_submit_add_habit)).perform(click());

        // Perform actions and checks on agenda screen.
        onView(withId(R.id.recyclerview_habits))
                .perform(scrollToPosition(expectedPos));
        onView(rowAtPos(withId(R.id.recyclerview_habits), expectedPos))
                .check(matches(hasDescendant(withText(habit.getName()))));
    }

    /**
     * Test that the display of the empty habit tracker is correct.
     */
    @Test
    public void testViewEmptyHabits() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthOfYear();

        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        String dateStr = dateFormatSymbols.getMonths()[month - 1]
                .substring(0, 3) + ". " + year;
        onView(withId(R.id.textview_habit_tracker_date))
                .check(matches(withText(dateStr)));

        LocalDate prevDayDate = LocalDate.now().minusDays(1);
        LocalDate prevDayDate2 = LocalDate.now().minusDays(2);
        String day = Integer.toString(today.getDayOfMonth());
        String prevDay = Integer.toString(prevDayDate.getDayOfMonth());
        String prevDay2 = Integer.toString(prevDayDate2.getDayOfMonth());

        onView(withId(R.id.textview_habit_day_curr))
                .check(matches(withText(day)));
        onView(withId(R.id.textview_habit_day_prev))
                .check(matches(withText(prevDay)));
        onView(withId(R.id.textview_habit_day_prev2))
                .check(matches(withText(prevDay2)));

        onView(withId(R.id.textview_empty_habits))
                .check(matches(withText("No habits")));
    }

    /**
     * Test adding a single habit.
     */
    @Test
    public void testAddHabit() {
        Habit habit = new Habit("Take a walk", new HashSet<>());
        addHabit(habit, 0);
    }

    /**
     * Test adding multiple habits.
     */
    @Test
    public void testAddMultHabits() {
        int newHabits = 5;
        for (int i = 0; i < newHabits; i++) {
            HashSet<LocalDate> completions = new HashSet<>();

            if (i % 2 == 0) {
                completions.add(LocalDate.now());
            }
            if (i % 3 == 0) {
                completions.add(LocalDate.now().minusDays(1));
            }
            if (i % 4 == 0) {
                completions.add(LocalDate.now().minusDays(2));
            }

            Habit habit = new Habit("Read for " + i + " minutes", completions);
            addHabit(habit, i);
        }
    }
}
