package com.example.measure.models.user;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import javax.inject.Inject;

/**
 * A repository using a local database for accessing the user data.
 */
public class LocalUserRepository implements UserRepository {
    private UserDao userDao;

    /**
     * Initialize member variables.
     *
     * @param userDao user database access object
     */
    @Inject
    public LocalUserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Retrieve the active task for the user from the database.
     *
     * @return the active task for the user.
     */
    @Override
    public LiveData<Task> getActiveTask() {
        return null;
    }

    /**
     * Update the active task for the user in the database.
     *
     * @param task the new active task to set
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public boolean updateActiveTask(Task task) {
        return false;
    }

    /**
     * Add the user to the database.
     *
     * @param user user to be added
     * @throws IllegalArgumentException when the new user would be invalid
     * @throws DBOperationException if the user could not be added
     */
    public void addUser(User user)
            throws IllegalArgumentException, DBOperationException {
        validateNewUser(user);
        userDao.addUser(user);
    }

    /**
     * Validate the new user's data.
     *
     * @param newUser the user to be added
     * @throws IllegalArgumentException when the new user's data is invalid
     */
    private void validateNewUser(User newUser) throws IllegalArgumentException {
        if (newUser.getUsername() == null) {
            throw new IllegalArgumentException("Missing username.");
        }
        else if (newUser.getPassword() == null) {
            throw new IllegalArgumentException("Missing password.");
        }
        else if (newUser.getEmail() == null) {
            throw new IllegalArgumentException("Missing email.");
        }

        if (!newUser.getUsername().matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("Username must only contain "
                    + "letters and numbers.");
        }
        if (newUser.getEmail().indexOf('@') < 0) {
            throw new IllegalArgumentException("Invalid email address.");
        }
    }

    /**
     * Return the user with the matching username and password.
     *
     * @param username username of the user
     * @param password password of the user
     * @return matching user or null if no such user was found
     * @throws DBOperationException if the user could not be fetched
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public User getUser(String username, String password)
            throws DBOperationException {
        return userDao.getUser(username, password).getValue();
    }
}