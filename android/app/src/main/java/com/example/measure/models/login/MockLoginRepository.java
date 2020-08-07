package com.example.measure.models.login;

import androidx.annotation.VisibleForTesting;

import com.example.measure.models.data.User;
import com.example.measure.utils.AuthenticationException;

import java.util.HashMap;
import java.util.Map;

/**
 * A fake repository for accessing the login session data.
 */
public class MockLoginRepository implements LoginRepository {
    public static final User DEFAULT_USER = new User(1, "Hello", null);
    private User currUser;
    private HashMap<String, User> usernameUserMap;

    /**
     * Initialize the current user.
     */
    public MockLoginRepository() {
        currUser = DEFAULT_USER;
        usernameUserMap = new HashMap<>();
    }

    /**
     * Retrieve the current user logged in.
     *
     * @return the user currently logged in (with their password not included)
     */
    @Override
    public User getCurrentUser() {
        return currUser;
    }

    /**
     * Start a login session for the user.
     *
     * @param username username of the user
     * @param password password of the user
     * @throws AuthenticationException if the username and password are invalid
     */
    @Override
    public void login(String username, String password)
            throws AuthenticationException {
        for (Map.Entry<String, User> entry : usernameUserMap.entrySet()) {
            if (entry.getKey().equals(username)
                    && entry.getValue().getPassword().equals(password)) {
                currUser = entry.getValue();
                return;
            }
        }

        throw new AuthenticationException("Invalid username or password");
    }

    /**
     * Clear the current login session.
     */
    @Override
    public void logout() {
        currUser = null;
    }

    /**
     * Add the user to the database.
     *
     * Note: This is not implemented for the mock login repository on purpose.
     *
     * @param user user to be added
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void addUser(User user) {
        usernameUserMap.put(user.getUsername(), user);
    }
}
