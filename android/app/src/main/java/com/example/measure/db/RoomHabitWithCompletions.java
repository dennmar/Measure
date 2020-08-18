package com.example.measure.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.measure.models.data.Habit;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A habit for the Room database with the habit completions.
 */
public class RoomHabitWithCompletions {
    @Embedded
    private RoomHabit rhabit;

    @Relation(
            parentColumn = "id",
            entityColumn = "habit_id"
    )
    private List<RoomHabitCompletion> rhabitCompletions;

    /**
     * Initialize member variables.
     */
    public RoomHabitWithCompletions() {
        rhabit = new RoomHabit();
        rhabitCompletions = new ArrayList<>();
    }

    /**
     * Initialize member variables from a Habit.
     */
    public RoomHabitWithCompletions(Habit habit) {
        rhabit = new RoomHabit();
        rhabit.setId(habit.getId());
        rhabit.setUserId(habit.getUserId());
        rhabit.setName(habit.getName());
        rhabitCompletions = new ArrayList<>();
    }

    /**
     * Return the Habit representation of this RoomHabit.
     *
     * @return the RoomHabit converted to a Habit
     */
    public Habit toHabit() {
        HashSet<LocalDate> completions = new HashSet<>();

        for (RoomHabitCompletion rhabitComp : rhabitCompletions) {
            completions.add(rhabitComp.getLocalCompletedDate());
        }

        return new Habit(rhabit.getId(), rhabit.getUserId(), rhabit.getName(),
                completions);
    }

    /* Getters and setters */

    public RoomHabit getRhabit() {
        return rhabit;
    }

    public void setRhabit(RoomHabit rhabit) {
        this.rhabit = rhabit;
    }

    public List<RoomHabitCompletion> getRhabitCompletions() {
        return rhabitCompletions;
    }

    public void setRhabitCompletions(List<RoomHabitCompletion> rhabitCompletions) {
        this.rhabitCompletions = rhabitCompletions;
    }
}
