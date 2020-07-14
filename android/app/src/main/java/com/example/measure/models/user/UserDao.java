package com.example.measure.models.user;

import com.example.measure.models.data.User;

/**
 * A user database access object.
 */
public interface UserDao {
    /**
     * Add a user to the database.
     *
     * @param user user to be added
     * @param password password of the user
     */
    void addUser(User user, String password);

    /**
     * Retrieve a user with the matching username and password.
     *
     * @param username username of the user to fetch
     * @param password password of the user to fetch
     * @return the matching user or null if no matching user was found
     */
    User getUser(String username, String password);
}
