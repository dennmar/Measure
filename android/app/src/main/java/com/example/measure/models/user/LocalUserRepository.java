package com.example.measure.models.user;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;

/**
 * A repository using a local database for accessing the user data.
 */
public class LocalUserRepository implements UserRepository {
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
     */
    public void addUser(User user) {

    }

    /**
     * Return the user with the matching username
     *
     * @param username username of the user
     * @return the matching user or null if no such user was found
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public User getUser(String username) {
        return null;
    }

}