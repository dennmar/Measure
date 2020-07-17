package com.example.measure.models.user;

import com.example.measure.models.data.User;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A fake user database access object.
 *
 * Note: this class is made singleton for use in login repository test.
 */
@Singleton
public class MockUserDao implements UserDao {
    private HashMap<String, User> usernameUserMap;

    /**
     * Initialize member variables.
     */
    @Inject
    public MockUserDao() {
        usernameUserMap = new HashMap<>();
    }

    /**
     * Add a user to the database.
     *
     * @param user user to be added
     */
    @Override
    public void addUser(User user) {
        usernameUserMap.put(user.getUsername(), user);
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
        for (Map.Entry<String, User> entry : usernameUserMap.entrySet()) {
            if (entry.getKey().equals(username)
                    && entry.getValue().getPassword().equals(password)) {
                User foundUser = entry.getValue();
                User returnUser = new User(foundUser.getId(),
                        foundUser.getUsername(), foundUser.getEmail(), null,
                        foundUser.getActiveTask());
                return returnUser;
            }
        }

        return null;
    }
}
