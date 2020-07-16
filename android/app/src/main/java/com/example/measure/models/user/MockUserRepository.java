package com.example.measure.models.user;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;

import java.util.HashMap;

/**
 * A fake repository for accessing user data.
 */
public class MockUserRepository implements UserRepository {
    private MutableLiveData<Task> activeTask;
    private HashMap<String, User> usernameUserMap;

    public MockUserRepository() {
        activeTask = new MutableLiveData<>();
        usernameUserMap = new HashMap<>();
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

    /**
     * Add the user to the database.
     *
     * @param user user to be added
     * @throws IllegalArgumentException when the new user would be invalid
     */
    public void addUser(User user) {
        if (user == null || user.getUsername() == null) {
            throw new IllegalArgumentException("User is null");
        }

        usernameUserMap.put(user.getUsername(), user);
    }

    /**
     * Return the user with the matching username
     *
     * @param username username of the user
     * @return the matching user or null if no such user was found
     */
    public User getUser(String username) {
        if (usernameUserMap.containsKey(username)) {
            return usernameUserMap.get(username);
        }

        return null;
    }
}