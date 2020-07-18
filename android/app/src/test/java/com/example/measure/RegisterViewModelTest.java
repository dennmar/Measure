package com.example.measure;

import android.os.Bundle;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.measure.di.components.DaggerTestRegisterViewModelComponent;
import com.example.measure.di.components.TestRegisterViewModelComponent;
import com.example.measure.features.register.RegisterViewModel;
import com.example.measure.models.data.User;
import com.example.measure.models.user.UserRepository;
import com.example.measure.utils.DBOperationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.example.measure.CustomMatchers.reflectEquals;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test the register view model.
 */
public class RegisterViewModelTest {
    // Execute background tasks synchronously (to allow LiveData to work).
    @Rule
    public TestWatcher rule = new InstantTaskExecutorRule();
    @Mock
    private Bundle savedInstanceState;

    RegisterViewModel rvm;
    UserRepository userRepo;

    /**
     * Create a register view model.
     */
    @Before
    public void initRegisterViewModel() {
        // Initialize mocks annotated with @Mock before each test method.
        MockitoAnnotations.initMocks(this);

        TestRegisterViewModelComponent testComponent =
                DaggerTestRegisterViewModelComponent.create();
        rvm = testComponent.rvmFactory().create(savedInstanceState);
        userRepo = testComponent.userRepository();
    }

    /**
     * Test adding a user.
     */
    @Test
    public void testAddUser() throws DBOperationException {
        User testUser = new User("test", "tester@gmail.com", "password");
        testUser.setId(1);
        rvm.addUser(testUser);

        User expectedUser = new User(testUser.getId(), testUser.getUsername(),
                testUser.getEmail(), null, testUser.getActiveTask());
        User getResult = userRepo.getUser(testUser.getUsername(),
                testUser.getPassword());
        assertThat(getResult, reflectEquals(expectedUser));
    }
}
