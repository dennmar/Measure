package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.DaggerTestLoginDaoComponent;
import com.example.measure.di.components.TestLoginDaoComponent;
import com.example.measure.models.data.User;
import com.example.measure.models.login.LoginDao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Run instrumented integration tests on the login DAO (and the Room database).
 */
@RunWith(AndroidJUnit4.class)
public class LoginDaoTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    LoginDao loginDao;
    MeasureApplication app = (MeasureApplication) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

    /**
     * Create a new login DAO.
     */
    @Before
    public void initLoginDao() {
        TestLoginDaoComponent loginDaoComponent =
                DaggerTestLoginDaoComponent.factory().newAppComponent(app);
        loginDao = loginDaoComponent.loginDao();

        // Clear all data before each test.
        loginDao.clearSession();
    }

    /**
     * Test getting the current user when there is no current user.
     */
    @Test
    public void testNoCurrentUser() {
        User getResult = loginDao.getCurrentUser();
        assertThat(getResult, equalTo(null));
    }

    /**
     * Test setting the current user.
     */
    @Test
    public void testSetCurrentUser() {
        User testUser = new User("Test", "test@test.com", "password");
        loginDao.setCurrentUser(testUser);

        User getResult = loginDao.getCurrentUser();
        assertThat(getResult.getUsername(), equalTo(testUser.getUsername()));
        assertThat(getResult.getPassword(), equalTo(testUser.getPassword()));
    }

    /**
     * Test clearing the login session.
     */
    @Test
    public void testClearSession() {
        User testUser = new User("Test", "test@test.com", "password");
        loginDao.setCurrentUser(testUser);

        User getResult = loginDao.getCurrentUser();
        assertThat(getResult.getUsername(), equalTo(testUser.getUsername()));
        assertThat(getResult.getPassword(), equalTo(testUser.getPassword()));

        loginDao.clearSession();
        User getResult2 = loginDao.getCurrentUser();
        assertThat(getResult2, equalTo(null));
    }

    /**
     * Test setting the current user to different users multiple times.
     */
    @Test
    public void testMultipleUserSwitch() {
        int numUsers = 5;

        for (int i = 0; i < numUsers; i++) {
            User testUser = new User("User#" + i, "test" + i + "@test.com", "password");
            loginDao.setCurrentUser(testUser);

            User getResult = loginDao.getCurrentUser();
            assertThat(getResult.getUsername(), equalTo(testUser.getUsername()));
            assertThat(getResult.getPassword(), equalTo(testUser.getPassword()));
        }
    }
}
