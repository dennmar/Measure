package com.example.measure.models.user;

import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Task;

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
}