package com.example.measure.models.data;

/**
 * A user of the measure application.
 */
public class User {
    public int id;
    public String username;
    public Task activeTask;

    /**
     * Initialize member variables.
     *
     * @param id         id of the user stored in the database
     * @param username   username of the user
     * @param activeTask active task for the user
     */
    public User(int id, String username, Task activeTask) {
        this.id = id;
        this.username = username;
        this.activeTask = activeTask;
    }

    @Override
    public String toString() {
        return "id: " + id  + ", "
                + "username: " + username + ", "
                + "activeTask: " + activeTask;
    }
}
