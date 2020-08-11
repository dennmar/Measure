package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.ApplicationComponent;
import com.example.measure.di.components.DaggerApplicationComponent;
import com.example.measure.features.EnterActivity;
import com.example.measure.features.register.RegisterFragment;
import com.example.measure.models.data.User;
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
 * Run instrumented integration tests on user registration using the production
 * Dagger modules.
 */
@RunWith(AndroidJUnit4.class)
public class RegisterProdTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    public TestWatcher instantTaskExecutorRule = new InstantTaskExecutorRule();
    public ActivityTestRule<EnterActivity> enterActivityTestRule =
            new ActivityTestRule<>(EnterActivity.class);
    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(instantTaskExecutorRule)
            .around(enterActivityTestRule);

    EnterActivity enterActivity;
    RegisterFragment registerFrag;
    MeasureApplication app = (MeasureApplication) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

    /**
     * Display the register fragment.
     */
    @Before
    public void initRegisterFragment() {
        // Set production application component.
        ApplicationComponent appComponent = DaggerApplicationComponent
                .factory()
                .newAppComponent(app);
        app.setAppComponent(appComponent);

        enterActivity = enterActivityTestRule.getActivity();
        registerFrag = new RegisterFragment();
        enterActivity.replaceFragment(registerFrag);

        // Clear all data before each test.
        app.clearAllData();
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

    /**
     * Test registering a new user without a username.
     */
    @Test
    public void testMissingUsername() {
        onView(withId(R.id.edittext_new_user_email))
                .perform(typeText("djhskdj@email.com"));
        onView(withId(R.id.edittext_new_user_password))
                .perform(typeText("password"));
        onView(withId(R.id.edittext_new_user_password2))
                .perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register_user))
                .perform(click());

        onView(withId(R.id.textview_register_error))
                .check(matches(withText("Missing username.")));
        onView(withId(R.id.btn_register_user)).check(matches(isDisplayed()));
    }

    /**
     * Test registering a new user with an invalid username.
     */
    @Test
    public void testInvalidUsername() {
        onView(withId(R.id.edittext_new_user_username))
                .perform(typeText("4.bidden!@[]"));
        onView(withId(R.id.edittext_new_user_email))
                .perform(typeText("em@ail.com"));
        onView(withId(R.id.edittext_new_user_password))
                .perform(typeText("password"));
        onView(withId(R.id.edittext_new_user_password2))
                .perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register_user))
                .perform(click());

        String errMsg = "Username must only contain letters and numbers.";
        onView(withId(R.id.textview_register_error))
                .check(matches(withText(errMsg)));
        onView(withId(R.id.btn_register_user)).check(matches(isDisplayed()));
    }

    /**
     * Test registering a new user with an invalid email.
     */
    @Test
    public void testInvalidEmail() {
        onView(withId(R.id.edittext_new_user_username))
                .perform(typeText("trask"));
        onView(withId(R.id.edittext_new_user_email))
                .perform(typeText("testemail"));
        onView(withId(R.id.edittext_new_user_password))
                .perform(typeText("password"));
        onView(withId(R.id.edittext_new_user_password2))
                .perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register_user))
                .perform(click());

        String errMsg = "Invalid email address.";
        onView(withId(R.id.textview_register_error))
                .check(matches(withText(errMsg)));
        onView(withId(R.id.btn_register_user)).check(matches(isDisplayed()));
    }

    /**
     * Test registering a new user with a different password for confirmation
     * than the first password entered.
     */
    @Test
    public void testMismatchedPasswords() {
        onView(withId(R.id.edittext_new_user_username))
                .perform(typeText("roik"));
        onView(withId(R.id.edittext_new_user_email))
                .perform(typeText("greenemail@color.com"));
        onView(withId(R.id.edittext_new_user_password))
                .perform(typeText("password"));
        onView(withId(R.id.edittext_new_user_password2))
                .perform(typeText("notpassword"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register_user))
                .perform(click());

        onView(withId(R.id.textview_register_error))
                .check(matches(withText("Passwords do not match.")));
        onView(withId(R.id.btn_register_user)).check(matches(isDisplayed()));
    }

    /**
     * Test registering a new user with a username that's already taken.
     */
    @Test
    public void testTakenUsername()
            throws InterruptedException, DBOperationException {
        // Wait for view model to be initialized.
        Thread.sleep(500);

        User takenUser = new User("takenuser", "email@taken.com", "password");
        registerFrag.registerUser(takenUser);

        onView(withId(R.id.edittext_new_user_username))
                .perform(typeText(takenUser.getUsername()));
        onView(withId(R.id.edittext_new_user_email))
                .perform(typeText("nottaken" + takenUser.getEmail()));
        onView(withId(R.id.edittext_new_user_password))
                .perform(typeText("nottaken" + takenUser.getPassword()));
        onView(withId(R.id.edittext_new_user_password2))
                .perform(typeText("nottaken" + takenUser.getPassword()));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register_user))
                .perform(click());

        onView(withId(R.id.textview_register_error))
                .check(matches(withText("Username is not available.")));
        onView(withId(R.id.btn_register_user)).check(matches(isDisplayed()));
    }

    /**
     * Test registering a new user with an email that's already taken.
     */
    @Test
    public void testTakenEmail()
            throws InterruptedException, DBOperationException {
        // Wait for view model to be initialized.
        Thread.sleep(500);

        User takenUser = new User("hellouser", "taken@gmail.com", "password");
        registerFrag.registerUser(takenUser);

        onView(withId(R.id.edittext_new_user_username))
                .perform(typeText("nottaken" + takenUser.getUsername()));
        onView(withId(R.id.edittext_new_user_email))
                .perform(typeText(takenUser.getEmail()));
        onView(withId(R.id.edittext_new_user_password))
                .perform(typeText("nottaken" + takenUser.getPassword()));
        onView(withId(R.id.edittext_new_user_password2))
                .perform(typeText("nottaken" + takenUser.getPassword()));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register_user))
                .perform(click());

        onView(withId(R.id.textview_register_error))
                .check(matches(withText("Email address is not available.")));
        onView(withId(R.id.btn_register_user)).check(matches(isDisplayed()));
    }
}
