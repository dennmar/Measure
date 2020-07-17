package com.example.measure;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestUserRepositoryComponent;
import com.example.measure.di.components.TestUserRepositoryComponent;
import com.example.measure.models.data.User;
import com.example.measure.models.user.UserRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import static com.example.measure.RegisterViewModelTest.reflectEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Unit test the user repository.
 */
public class UserRepositoryTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();

    UserRepository userRepo;

    /**
     * Create a new user repository.
     */
    @Before
    public void initUserRepository() {
        TestUserRepositoryComponent userRepositoryComponent =
                DaggerTestUserRepositoryComponent.create();
        userRepo = userRepositoryComponent.userRepository();
    }

    /**
     * Test adding a user successfully.
     */
    @Test
    public void testAddUser() {
        User testUser = new User("test", "tester@yahoo.com", "nothing");
        testUser.setId(1);
        userRepo.addUser(testUser);

        User expectedUser = new User(testUser.getId(), testUser.getUsername(),
                testUser.getEmail(), null, testUser.getActiveTask());
        User getResult = userRepo.getUser(testUser.getUsername(),
                testUser.getPassword());
        assertThat(getResult, reflectEquals(expectedUser));
    }

    /**
     * Test adding a user without a password.
     */
    @Test
    public void testAddUserMissingPassword() {
        User testUser = new User("exam", "red@blue.com", null);
        testUser.setId(1);

        try {
            userRepo.addUser(testUser);
            assertThat(false, equalTo(true));
        }
        catch (IllegalArgumentException e) {
            // Expected behavior.
        }
    }

    /**
     * Test adding a user with an invalid username.
     */
    @Test
    public void testAddUserInvalidUsername() {
        User testUser1 = new User("ran.p3ge>", "armadillo@animal.net", "what");
        User testUser2 = new User("hello there", "greetings@abc.def", "why");
        User testUser3 = new User("/@.@\\", "enter@leave.bye", "when");

        testUser1.setId(1);
        testUser2.setId(2);
        testUser3.setId(3);

        User[] users = {testUser1, testUser2, testUser3};

        for (int i = 0; i < users.length; i++) {
            try {
                userRepo.addUser(users[i]);
                assertThat(i, equalTo(users.length));
            } catch (IllegalArgumentException e) {
                // Expected behavior.
            }
        }
    }

    /**
     * Test adding a user with an invalid email.
     */
    @Test
    public void testAddUserInvalidEmail() {
        User testUser = new User("Joy", "glademail", "happy");
        testUser.setId(1);

        try {
            userRepo.addUser(testUser);
            assertThat(false, equalTo(true));
        }
        catch (IllegalArgumentException e) {
            // Expected behavior.
        }
    }
}
