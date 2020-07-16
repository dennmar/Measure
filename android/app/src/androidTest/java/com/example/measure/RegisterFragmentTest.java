package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.ApplicationComponent;
import com.example.measure.di.components.DaggerTestRegisterFragmentComponent;
import com.example.measure.features.EnterActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Run instrumented integration tests with the enter activity and register
 * fragment.
 */
@RunWith(AndroidJUnit4.class)
public class RegisterFragmentTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    ActivityScenario<EnterActivity> enterActScenario;
    MeasureApplication mockApp = (MeasureApplication) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

    /**
     * Launch the enter activity (which displays the register fragment first).
     */
    @Before
    public void initRegisterFragment() {
        ApplicationComponent testComponent =
                DaggerTestRegisterFragmentComponent.create();
        mockApp.setAppComponent(testComponent);
        enterActScenario = ActivityScenario.launch(EnterActivity.class);
    }

    /**
     * Test that the register fragment is being displayed.
     */
    @Test
    public void testDisplay() {
        onView(withId(R.id.edittext_new_user_username))
                .check(matches(isDisplayed()));
        onView(withId(R.id.btn_register_user)).check(matches(isDisplayed()));
    }

    /**
     * Test successful register of a user.
     */
    @Test
    public void testAddUser() {
        onView(withId(R.id.edittext_new_user_username))
                .perform(typeText("testuser"));
        onView(withId(R.id.edittext_new_user_email))
                .perform(typeText("test@email.com"));
        onView(withId(R.id.edittext_new_user_password))
                .perform(typeText("password"));
        onView(withId(R.id.edittext_new_user_password2))
                .perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register_user))
                .perform(click());

        // Check that the login screen is displayed.
        onView(withId(R.id.edittext_login_username))
                .check(matches(isDisplayed()));
        onView(withId(R.id.btn_login_user)).check(matches(isDisplayed()));
    }
}
