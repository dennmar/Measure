package com.example.measure.models.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Task;

/**
 * A fake repository for accessing user data.
 */
public class MockUserRepository implements UserRepository {
    private MutableLiveData<Task> activeTask;

    public MockUserRepository() {
        activeTask = new MutableLiveData<>();
    }

    /**
     * Retrieve the active task for the user from the database.
     *
     * @return the active task for the user.
     */
    @Override
    public LiveData<Task> getActiveTask() {
        return activeTask;
    }

    /**
     * Update the active task for the user in the database.
     *
     * @param task the new active task to set
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public boolean updateActiveTask(Task task) {
        activeTask.setValue(task);
        return true;
    }
}