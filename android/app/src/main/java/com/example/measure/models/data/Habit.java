package com.example.measure.models.data;

import org.joda.time.LocalDate;

import java.util.HashSet;

/**
 * An action that can be completed on multiple days.
 */
public class Habit {
    private long id;
    private long userId;
    private String name;
    private HashSet<LocalDate> completions;

    /**
     * Initialize member variables.
     *
     * @param name        name of the habit
     * @param completions dates when the habit has been completed
     */
    public Habit(String name, HashSet<LocalDate> completions) {
        this.id = 0;
        this.userId = -1;
        this.name = name;
        this.completions = completions;
    }

    /**
     * Initialize member variables.
     *
     * @param id          id of the habit
     * @param userId      id of the user who owns the habit
     * @param name        name of the habit
     * @param completions dates when the habit has been completed
     */
    public Habit(long id, long userId, String name,
            HashSet<LocalDate> completions) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.completions = completions;
    }


    /* Getters and setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<LocalDate> getCompletions() {
        return completions;
    }

    public void setCompletions(HashSet<LocalDate> completions) {
        this.completions = completions;
    }
}
