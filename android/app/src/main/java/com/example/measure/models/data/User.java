package com.example.measure.models.data;

/**
 * A user of the measure application.
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Task activeTask;

    /**
     * Initialize member variables.
     *
     * @param username username of the user
     * @param email    email of the user
     * @param password password of the user
     */
    public User(String username, String email, String password) {
        this.id = -1;
        this.username = username;
        this.email = email;
        this.password = password;
        this.activeTask = null;
    }

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
        this.email = null;
        this.password = null;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Task getActiveTask() {
        return activeTask;
    }

    public void setActiveTask(Task activeTask) {
        this.activeTask = activeTask;
    }
}
