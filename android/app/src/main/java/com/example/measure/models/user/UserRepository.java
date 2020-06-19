package com.example.measure.models.user;

import androidx.lifecycle.LiveData;

import com.example.measure.models.data.Task;

/**
 * A repository for accessing user data.
 */
public interface UserRepository {
    /**
     * Retrieve the active task for the user from the database.
     *
     * @return the active task for the user.
     */
    public LiveData<Task> getActiveTask();

    /**
     * Update the active task for the user in the database.
     *
     * @param task the new active task to set
     * @return true if the operation was successful; false otherwise
     */
    public boolean updateActiveTask(Task task);
}