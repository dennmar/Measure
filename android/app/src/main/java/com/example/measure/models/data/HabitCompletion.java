package com.example.measure.models.data;

import org.joda.time.LocalDate;

/**
 * An occurrence when a habit was completed.
 */
public class HabitCompletion {
    private long id;
    private long habitId;
    private LocalDate localCompletionDate;

    /**
     * Initialize member variables.
     *
     * @param habitId             id of the habit that was completed
     * @param localCompletionDate local date without a time zone when habit
     *                            was completed
     */
    public HabitCompletion(long habitId, LocalDate localCompletionDate) {
        this.id = 0;
        this.habitId = habitId;
        this.localCompletionDate = localCompletionDate;
    }

    @Override
    public String toString() {
        return "id: " + id
                + ", habitId: " + habitId
                + ", localCompletionDate: " + localCompletionDate;
    }

    /* Getters and setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHabitId() {
        return habitId;
    }

    public void setHabitId(long habitId) {
        this.habitId = habitId;
    }

    public LocalDate getLocalCompletionDate() {
        return localCompletionDate;
    }

    public void setLocalCompletionDate(LocalDate localCompletionDate) {
        this.localCompletionDate = localCompletionDate;
    }
}
