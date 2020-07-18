package com.example.measure.models.user;

import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

/**
 * A user database access object.
 */
public interface UserDao {
    /**
     * Add a user to the database.
     *
     * @param user user to be added
     * @throws DBOperationException if the user could not be added
     */
    void addUser(User user) throws DBOperationException;

    /**
     * Retrieve a user from the database with the matching username and
     * password.
     *
     * @param username username of the user to fetch
     * @param password password of the user to fetch
     * @return the matching user or null if no matching user was found
     * @throws DBOperationException if the user could not be fetched
     */
    User getUser(String username, String password) throws DBOperationException;
}
