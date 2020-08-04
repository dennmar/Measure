package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.RoomDatabase;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.components.DaggerTestUserDaoComponent;
import com.example.measure.di.components.TestUserDaoComponent;
import com.example.measure.models.data.User;
import com.example.measure.models.user.UserDao;
import com.example.measure.utils.DBOperationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import static com.example.measure.CustomMatchers.reflectEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Run instrumented integration tests on the user DAO (and the Room database).
 */
@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    UserDao userDao;
    RoomDatabase testRoomDb;
    MeasureApplication app = (MeasureApplication) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

    /**
     * Create a new user DAO.
     */
    @Before
    public void initUserDao() {
        TestUserDaoComponent userDaoComponent =
                DaggerTestUserDaoComponent.factory().newAppComponent(app);
        userDao = userDaoComponent.userDao();

        // Clear all data before each test.
        testRoomDb = userDaoComponent.measureRoomDatabase();
        testRoomDb.clearAllTables();
    }

    @After
    public void closeConnections() {
        testRoomDb.close();
    }

    /**
     * Test getting a user that doesn't exist.
     */
    @Test
    public void testGetMissingUser() throws DBOperationException {
        User testUser = new User("Nonexistent", "no@no.com", "none");
        User getResult = userDao.getUser(testUser.getUsername(),
                testUser.getPassword()).getValue();
        assertThat(getResult, equalTo(null));
    }

    /**
     * Test adding a user successfully.
     */
    @Test
    public void testAddUser() throws DBOperationException {
        User testUser = new User("Keyboard", "type@fast.com", "qwerty");
        testUser.setId(1);
        userDao.addUser(testUser);

        User expectedUser = new User(testUser.getId(), testUser.getUsername(),
                testUser.getEmail(), null, testUser.getActiveTask());
        User getResult = userDao.getUser(testUser.getUsername(),
                testUser.getPassword()).getValue();
        assertThat(getResult, reflectEquals(expectedUser));
    }

    /**
     * Test adding a user with a username that already exists.
     */
    @Test
    public void testAddUserDupUsername() throws DBOperationException {
        User testUser = new User("Keyboard", "type@fast.com", "qwerty");
        testUser.setId(1);
        userDao.addUser(testUser);

        User expectedUser = new User(testUser.getId(), testUser.getUsername(),
                testUser.getEmail(), null, testUser.getActiveTask());
        User getResult = userDao.getUser(testUser.getUsername(),
                testUser.getPassword()).getValue();
        assertThat(getResult, reflectEquals(expectedUser));

        User testUser2 = new User(testUser.getUsername(), "laptop@pc.io",
                "asdf");
        testUser2.setId(2);

        try {
            userDao.addUser(testUser2);
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }
    }

    /**
     * Test adding a user with an email that already exists.
     */
    @Test
    public void testAddUserDupEmail() throws DBOperationException {
        User testUser = new User("Keyboard", "type@fast.com", "qwerty");
        testUser.setId(1);
        userDao.addUser(testUser);

        User expectedUser = new User(testUser.getId(), testUser.getUsername(),
                testUser.getEmail(), null, testUser.getActiveTask());
        User getResult = userDao.getUser(testUser.getUsername(),
                testUser.getPassword()).getValue();
        assertThat(getResult, reflectEquals(expectedUser));

        User testUser2 = new User("Typer", testUser.getEmail(), "asdf");
        testUser2.setId(2);

        try {
            userDao.addUser(testUser2);
            assertThat(false, equalTo(true));
        }
        catch (DBOperationException e) {
            // Expected behavior.
        }
    }

    /**
     * Test adding multiple users.
     */
    @Test
    public void testAddMultUsers() throws DBOperationException {
        int userAmt = 5;

        for (int i = 1; i <= userAmt; i++) {
            User testUser = new User("User#" + i, "email" + i + "@gmail.com",
                    "pass");
            testUser.setId(i);
            userDao.addUser(testUser);
        }

        for (int i = 1; i <= userAmt; i++) {
            User expectedUser = new User(i, "User#" + i,
                    "email" + i + "@gmail.com", null, null);
            User getResult = userDao.getUser("User#" + i, "pass").getValue();
            assertThat(getResult, reflectEquals(expectedUser));
        }
    }
}
