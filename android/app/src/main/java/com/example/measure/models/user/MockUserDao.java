package com.example.measure.models.user;

import com.example.measure.models.data.User;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * A fake user database access object.
 */
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
    public void asyncAddUser(User user) {
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
    public User asyncGetUser(String username, String password) {
        User foundUser = usernameUserMap.get(username);
        if (foundUser == null) {
            return null;
        }

        User returnUser = new User(foundUser.getId(), foundUser.getUsername(),
                foundUser.getEmail(), null, foundUser.getActiveTask());
        return returnUser;
    }
}
