package com.example.measure.models.user;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;

/**
 * A repository for accessing user data.
 */
public interface UserRepository {
    /**
     * Retrieve the active task for the user from the database.
     *
     * @return the active task for the user.
     */
    LiveData<Task> getActiveTask();

    /**
     * Update the active task for the user in the database.
     *
     * @param task the new active task to set
     * @return true if the operation was successful; false otherwise
     */
    boolean updateActiveTask(Task task);

    /**
     * Add the user to the database.
     *
     * @param user user to be added
     * @throws IllegalArgumentException when the new user would be invalid
     */
    void addUser(User user) throws IllegalArgumentException;

    /**
     * Return the user with the matching username
     *
     * @param username username of the user
     * @return the matching user or null if no such user was found
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    User getUser(String username);
}