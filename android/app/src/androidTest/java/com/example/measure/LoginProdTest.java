package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.ApplicationComponent;
import com.example.measure.di.components.DaggerApplicationComponent;
import com.example.measure.features.EnterActivity;
import com.example.measure.features.login.LoginFragment;
import com.example.measure.utils.DBOperationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Run instrumented integration tests on user login using the production Dagger
 * modules.
 */
@RunWith(AndroidJUnit4.class)
public class LoginProdTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    public TestWatcher instantTaskExecutorRule = new InstantTaskExecutorRule();
    public ActivityTestRule<EnterActivity> enterActivityTestRule =
            new ActivityTestRule<>(EnterActivity.class);
    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(instantTaskExecutorRule)
            .around(enterActivityTestRule);

    EnterActivity enterActivity;
    LoginFragment loginFrag;
    MeasureApplication app = (MeasureApplication) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

    /**
     * Launch the enter activity and display the login fragment.
     */
    @Before
    public void initLoginFragment() {
        ApplicationComponent appComponent = DaggerApplicationComponent
                .factory()
                .newAppComponent(app);
        app.setAppComponent(appComponent);

        enterActivity = enterActivityTestRule.getActivity();
        loginFrag = new LoginFragment();
        enterActivity.replaceFragment(loginFrag);

        app.clearAllData();
    }

    /**
     * Test that the login fragment is being displayed.
     */
    @Test
    public void testDisplay() {
        onView(withId(R.id.edittext_login_username)).check(matches(isDisplayed()));
        onView(withId(R.id.edittext_login_password)).check(matches(isDisplayed()));
    }

    /**
     * Test successful login.
     */
    @Test
    public void testLogin() throws DBOperationException, InterruptedException {
        // Wait for view model to be initialized.
        Thread.sleep(500);

        String username = "Test";
        String password = "password";

        loginFrag.addUser(username, password);

        onView(withId(R.id.edittext_login_username))
                .perform(typeText(username));
        onView(withId(R.id.edittext_login_password))
                .perform(typeText(password));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_login_user)).perform(click());

        // Check that the measure activity is displayed.
        onView(withId(R.id.measure_act_container))
                .check(matches(isDisplayed()));
    }

    /**
     * Test logging in with an invalid account.
     */
    @Test
    public void testInvalidLogin() throws InterruptedException {
        // Wait for view model to be initialized.
        Thread.sleep(500);

        String username = "Nothing";
        String password = "DNE";

        onView(withId(R.id.edittext_login_username))
                .perform(typeText(username));
        onView(withId(R.id.edittext_login_password))
                .perform(typeText(password));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_login_user)).perform(click());

        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText("Invalid username or password.")));
        onView(withId(R.id.btn_login_user))
                .check(matches(isDisplayed()));
    }
}
