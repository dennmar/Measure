package com.example.measure.models.data;

import org.joda.time.LocalDate;

import java.util.HashSet;

/**
 * An action that can be completed on multiple days.
 */
public class Habit {
    private String name;
    private HashSet<LocalDate> completions;

    /**
     * Initialize member variables
     *
     * @param name        name of the habit
     * @param completions dates when the habit has been completed
     */
    public Habit(String name, HashSet<LocalDate> completions) {
        this.name = name;
        this.completions = completions;
    }
}
