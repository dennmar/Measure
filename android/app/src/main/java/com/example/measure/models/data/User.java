package com.example.measure.models.data;

/**
 * A user of the measure application.
 */
public class User {
    private int id;
    private String username;
    private Task activeTask;

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

    /* Getters and setters */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Task getActiveTask() {
        return activeTask;
    }

    public void setActiveTask(Task activeTask) {
        this.activeTask = activeTask;
    }
}
