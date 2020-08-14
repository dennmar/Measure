package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.ApplicationComponent;
import com.example.measure.di.components.DaggerTestAgendaFragmentComponent;
import com.example.measure.features.MeasureActivity;
import com.example.measure.features.habit_tracker.HabitTrackerFragment;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestWatcher;

import java.text.DateFormatSymbols;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
     * Test that the habit tracker fragment is being displayed.
     */
    @Test
    public void testDisplay() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthOfYear();

        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        String dateStr = dateFormatSymbols.getMonths()[month - 1]
                .substring(0, 3) + ". " + year;
        onView(withId(R.id.textview_habit_tracker_date))
                .check(matches(withText(dateStr)));
    }
}
