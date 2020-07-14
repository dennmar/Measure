package com.example.measure;

import com.example.measure.di.components.DaggerTestLoginRepositoryComponent;
import com.example.measure.di.components.TestLoginRepositoryComponent;
import com.example.measure.models.data.User;
import com.example.measure.models.login.LoginRepository;
import com.example.measure.utils.AuthenticationException;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test the login repository.
 */
public class LoginRepositoryTest {
    LoginRepository loginRepo;
    UserDao mockUserDao;

    /**
     * Create a new login repository.
     */
    @Before
    public void initLoginRepo() {
        TestLoginRepositoryComponent loginRepoComponent =
                DaggerTestLoginRepositoryComponent.create();
        loginRepo = loginRepoComponent.loginRepository();
        mockUserDao = loginRepoComponent.mockUserDao();
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
     * Test logging in with invalid credentials.
     */
    @Test
    public void testInvalidLogin() {
        String username = "test";
        String password = "password";

        try {
            loginRepo.login(username, password);
            assertThat(false, equalTo(true));
        }
        catch (AuthenticationException e) {
            // Expected behavior.
        }

        User getResult = loginRepo.getCurrentUser();
        assertThat(getResult, equalTo(null));
    }

    /**
     * Test logging in with valid credentials.
     */
    @Test
    public void testLogin() throws AuthenticationException {
        User testUser = new User(1, "test", null);
        String password = "password";
        mockUserDao.addUser(testUser, password);

        loginRepo.login(testUser.getUsername(), password);
        User getResult = loginRepo.getCurrentUser();
        assertThat(getResult, equalTo(testUser));
    }

    /**
     * Test clearing the current login session.
     */
    @Test
    public void testClearSession() {
        User testUser = new User(1, "test", null);
        String password = "password";
        mockUserDao.addUser(testUser, password);

        loginRepo.login(testUser.getUsername(), password);
        User getResult = loginRepo.getCurrentUser();
        assertThat(getResult, equalTo(testUser));

        loginRepo.logout();
        User getResult2 = loginRepo.getCurrentUser();
        assertThat(getResult2, equalTo(null));
    }
}
