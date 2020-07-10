package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.ApplicationComponent;
import com.example.measure.di.components.DaggerTestAgendaFragmentComponent;
import com.example.measure.features.agenda.AgendaFragment;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import java.text.DateFormatSymbols;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.measure.CustomMatchers.rowAtPos;

/**
 * Unit test the agenda fragment.
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedAgendaFragmentTest {
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    MeasureApplication mockApp;
    FragmentScenario<AgendaFragment> agendaFragScenario;

    /**
     * Create a new agenda fragment.
     */
    @Before
    public void initAgendaFragment() {
        mockApp = (MeasureApplication) InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext()
                .getApplicationContext();
        ApplicationComponent testComponent =
                DaggerTestAgendaFragmentComponent.create();
        mockApp.setAppComponent(testComponent);

        agendaFragScenario = FragmentScenario
                .launchInContainer(AgendaFragment.class);
    }

    /**
     * Test that the display of the empty agenda is correct.
     */
    @Test
    public void testViewEmptyAgenda() {
        onView(withId(R.id.add_task_button)).check(matches(isDisplayed()));
        onView(withId(R.id.agenda_recycler)).check(matches(isDisplayed()));

        LocalDate agendaStartDate = LocalDate.now().minusDays(3);
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();

        // Check the seven day window (three days before, today, and the three
        // following days).
        for (int i = 0; i < 7; i++) {
            LocalDate agendaDate = agendaStartDate.plusDays(i);
            int year = agendaDate.getYear();
            int month = agendaDate.getMonthOfYear();
            int day = agendaDate.getDayOfMonth();
            String dateStr = dateFormatSymbols.getMonths()[month - 1] + " " + day
                    + ", " + year;

            onView(withId(R.id.agenda_recycler))
                    .perform(scrollToPosition(i * 2));
            onView(rowAtPos(withId(R.id.agenda_recycler), i * 2))
                    .check(matches(hasDescendant(withText(dateStr))));
            onView(withId(R.id.agenda_recycler))
                    .perform(scrollToPosition((i * 2) + 1));
            onView(rowAtPos(withId(R.id.agenda_recycler), (i * 2) + 1))
                    .check(matches(hasDescendant(withText("No tasks"))));
        }
    }

    /**
     * Test adding a task to the agenda.
     */
    @Test
    public void testAddTask() {

    }

    /**
     * Test adding a task to the agenda that is currently outside of the
     * agenda's date range.
     */
    @Test
    public void testAddNonVisibleTask() {

    }

    /**
     * Test that the display of a filled agenda is correct.
     */
    @Test
    public void testViewFilledAgenda() {

    }

    /**
     * Test loading more of the agenda by scrolling up.
     */
    @Test
    public void testUpScrollLoad() {

    }

    /**
     * Test loading more of the agenda by scrolling down.
     */
    @Test
    public void testDownScrollLoad() {

    }

    /**
     * Test editing a task.
     */
    @Test
    public void testEditTask() {

    }

    /**
     * Test completing a task.
     */
    @Test
    public void testCompleteTask() {

    }

    /**
     * Test marking a task as incomplete.
     */
    @Test
    public void testIncompleteTask() {

    }

    /**
     * Test deleting a task.
     */
    @Test
    public void testDeleteTask() {

    }

    /**
     * Test setting a task as active.
     */
    @Test
    public void testSetActiveTask() {

    }
}
