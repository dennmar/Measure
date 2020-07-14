package com.example.measure;

import android.widget.DatePicker;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.ApplicationComponent;
import com.example.measure.di.components.DaggerTestAgendaFragmentComponent;
import com.example.measure.features.MeasureActivity;
import com.example.measure.models.data.Task;
import com.example.measure.utils.StringConverter;

import org.hamcrest.Matchers;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import java.text.DateFormatSymbols;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.measure.CustomMatchers.rowAtPos;

/**
 * Run integration tests with measure activity and the agenda fragment.
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedAgendaFragmentTest {
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    MeasureApplication mockApp;
    ActivityScenario<MeasureActivity> measureActScenario;

    /**
     * Launch the measure activity (which displays the agenda fragment first).
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

        measureActScenario = ActivityScenario.launch(MeasureActivity.class);
    }

    /**
     * Add a task to the agenda.
     */
    private void addTask(Task task, int expectedAgendaPos) {
        LocalDate taskDueDate = task.getLocalDueDate();
        String dateStr = StringConverter.localDateToString(taskDueDate);
        int day = taskDueDate.getDayOfMonth();
        int month = taskDueDate.getMonthOfYear();
        int year = taskDueDate.getYear();

        // Open task date picker page from agenda screen.
        onView(withId(R.id.btn_add_task)).perform(click());

        // Perform actions and checks on task date picker screen.
        onView(withId(R.id.edittext_new_task_name))
                .perform(typeText(task.getName()));
        onView(withId(R.id.btn_new_task_datepicker))
                .perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month, day));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.textview_new_task_date))
                .check(matches(withText(dateStr)));
        onView(withId(R.id.btn_submit_add_task)).perform(click());

        // Perform actions and checks on agenda screen.
        onView(withId(R.id.agenda_recycler))
                .perform(scrollToPosition(expectedAgendaPos));
        onView(rowAtPos(withId(R.id.agenda_recycler), expectedAgendaPos))
                .check(matches(hasDescendant(withText(task.getName()))));
    }

    /**
     * Test that the display of the empty agenda is correct.
     */
    @Test
    public void testViewEmptyAgenda() {
        onView(withId(R.id.btn_add_task)).check(matches(isDisplayed()));
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
        Task task = new Task();
        task.setName("Walk the dog");
        task.setLocalDueDate(LocalDate.now());

        int agendaDayRange = 7;
        int todayIndex = agendaDayRange / 2;
        int todayDateRowPos = todayIndex * 2;
        int expectedTaskPos = todayDateRowPos + 1;

        addTask(task, expectedTaskPos);
    }

    /**
     * Test that multiple tasks are displayed correctly.
     */
    @Test
    public void testAddMultipleTasks() {
        LocalDate agendaStartDate = LocalDate.now().minusDays(3);

        for (int i = 0; i < 7; i++) {
            Task task = new Task();
            task.setName("Practice piano for " + i + " minutes");
            task.setLocalDueDate(agendaStartDate.plusDays(i));
            addTask(task, i * 2 + 1);
        }

        // Check that an additional task under a date is displayed correctly.
        Task task = new Task();
        task.setName("Study music theory");
        task.setLocalDueDate(agendaStartDate.plusDays(2));
        addTask(task, 6);
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
     * Test adding a task to the agenda that is currently outside of the
     * agenda's date range.
     */
    @Test
    public void testAddNonVisibleTask() {

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
