package com.example.measure.models.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A fake repository for accessing user data.
 *
 * Note: this class is made singleton for use in the register view model test.
 */
@Singleton
public class MockUserRepository implements UserRepository {
    private MutableLiveData<Task> activeTask;
    private HashMap<String, User> usernameUserMap;

    @Inject
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
     * @param password password of the user
     * @return the matching user or null if no such user was found
     */
    public User getUser(String username, String password) {
        if (usernameUserMap.containsKey(username)) {
            User foundUser = usernameUserMap.get(username);
            if (foundUser.getPassword().equals(password)) {
                return new User(foundUser.getId(), foundUser.getUsername(),
                        foundUser.getEmail(), null,
                        foundUser.getActiveTask());
            }
        }

        return null;
    }
}