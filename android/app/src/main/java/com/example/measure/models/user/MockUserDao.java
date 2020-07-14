package com.example.measure.models.user;

import com.example.measure.models.data.User;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * A fake user database access object.
 */
public class MockUserDao implements UserDao {
    private HashMap<User, String> users;

    /**
     * Initialize member variables.
     */
    @Inject
    public MockUserDao() {
        users = new HashMap<>();
    }

    /**
     * Add a user to the database.
     *
     * @param user user to be added
     * @param password password of the user
     */
    @Override
    public void addUser(User user, String password) {
        users.put(user, password);
    }

    /**
     * Retrieve a user with the matching username and password.
     *
     * @param username username of the user to fetch
     * @param password password of the user to fetch
     * @return the matching user or null if no matching user was found
     */
    @Override
    public User getUser(String username, String password) {
        for (Map.Entry<User, String> userPasswordEntry : users.entrySet()) {
            if (userPasswordEntry.getKey().getUsername().equals(username)
                    && userPasswordEntry.getValue().equals(password)) {
                return userPasswordEntry.getKey();
            }
        }

        return null;
    }
}
