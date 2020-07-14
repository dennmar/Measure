package com.example.measure;

import com.example.measure.di.components.DaggerTestLoginRepositoryComponent;
import com.example.measure.di.components.TestLoginRepositoryComponent;
import com.example.measure.models.data.User;
import com.example.measure.models.login.LoginRepository;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test the login repository.
 */
public class LoginRepositoryTest {
    LoginRepository loginRepo;

    /**
     * Create a new login repository.
     */
    @Before
    public void initLoginRepo() {
        TestLoginRepositoryComponent loginRepoComponent =
                DaggerTestLoginRepositoryComponent.create();
        loginRepo = loginRepoComponent.loginRepository();
    }

    /**
     * Test getting the current user when there is no active login session.
     */
    @Test
    public void testNoSession() {
        User getResult = loginRepo.getCurrentUser();
        assertThat(getResult, equalTo(null));
    }

    /**
     * Test setting the current user of the login session.
     */
    @Test
    public void testSetCurrentUser() {
        User testUser = new User(1, "test", null);
        loginRepo.setCurrentUser(testUser);

        User getResult = loginRepo.getCurrentUser();
        assertThat(getResult, equalTo(testUser));
    }

    /**
     * Test clearing the current login session.
     */
    @Test
    public void testClearSession() {
        User testUser = new User(1, "test", null);
        loginRepo.setCurrentUser(testUser);

        User getResult = loginRepo.getCurrentUser();
        assertThat(getResult, equalTo(testUser));

        loginRepo.clearSession();
        User getResult2 = loginRepo.getCurrentUser();
        assertThat(getResult2, equalTo(null));
    }
}
