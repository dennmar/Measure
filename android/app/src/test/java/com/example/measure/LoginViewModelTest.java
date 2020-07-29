package com.example.measure;

import android.os.Bundle;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestLoginViewModelComponent;
import com.example.measure.di.components.TestLoginViewModelComponent;
import com.example.measure.features.login.LoginViewModel;
import com.example.measure.utils.AuthenticationException;
import com.example.measure.utils.DBOperationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test the login view model.
 */
public class LoginViewModelTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();
    @Mock
    private Bundle savedInstanceState;

    LoginViewModel lvm;

    /**
     * Create a login view model.
     */
    @Before
    public void initLoginViewModel() {
        // Initialize mocks annotated with @Mock before each test method.
        MockitoAnnotations.initMocks(this);

        TestLoginViewModelComponent testComponent =
                DaggerTestLoginViewModelComponent.create();
        lvm = testComponent.lvmFactory().create(savedInstanceState);
    }

    /**
     * Test logging in with an invalid username and password.
     */
    @Test
    public void testInvalidLogin() throws DBOperationException {
        String username = "Frog";
        String password = "Toad";

        try {
            lvm.login(username, password);
            assertThat(false, equalTo(true));
        }
        catch (AuthenticationException e) {
            // Expected behavior.
        }
    }

    /**
     * Test logging in with a valid username and password.
     */
    @Test
    public void testValidLogin()
            throws AuthenticationException, DBOperationException {
        String username = "York";
        String password = "Cheesecake";
        lvm.addUser(username, password);
        lvm.login(username, password);
    }
}
