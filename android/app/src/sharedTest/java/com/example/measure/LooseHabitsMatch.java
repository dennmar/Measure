package com.example.measure;

import com.example.measure.models.data.Habit;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

/**
 * Check if a list of habits loosely match another list of habits (ignoring
 * the order of the list and the id numbers of the habits).
 */
public class LooseHabitsMatch extends TypeSafeMatcher<List<Habit>> {
    private List<Habit> habits;

    /**
     * Initialize member variables.
     *
     * @param habits list of habits to be used for comparison
     */
    public LooseHabitsMatch(List<Habit> habits) {
        this.habits = habits;
    }

    /**
     * Return a matcher with the given habits to check for a loose match.
     *
     * @param habits list of habits to be used for comparison
     * @return a matcher for a list of habits that checks for a loose match
     */
    public static Matcher<List<Habit>> looseHabitsMatch(List<Habit> habits) {
        return new LooseHabitsMatch(habits);
    }

    /**
     * Check if the given list of habits loosely matches (only ignoring order
     * and habit id but checking for equality on fields).
     *
     * @param otherHabits list of habits to be compared
     * @return true if the list of habits loosely matches; false otherwise
     */
    @Override
    protected boolean matchesSafely(List<Habit> otherHabits) {
        if (habits.size() != otherHabits.size()) {
            return false;
        }

        for (int i = 0; i < habits.size(); i++) {
            if (!habitsLooselyMatch(habits.get(i), otherHabits.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if the two habits loosely match (only ignoring habit id).
     *
     * @param h1 first habit to be compared
     * @param h2 second habit to be compared
     * @return true if the habits loosely match; false otherwise
     */
    private boolean habitsLooselyMatch(Habit h1, Habit h2) {
        if (h1 == null && h2 == null) {
            return true;
        }
        else if (h1 != null && h2 != null) {
            if (h1.getName() == null && h2.getName() != null) {
                return false;
            }
            else if (h1.getName() != null
                    && !h1.getName().equals(h2.getName())) {
                return false;
            }

            if (h1.getCompletions() == null && h2.getCompletions() != null) {
                return false;
            }
            else if (h1.getCompletions() != null
                    && !h1.getCompletions().equals(h2.getCompletions())) {
                return false;
            }

            return h1.getUserId() == h2.getUserId();
        }
        else {
            return false;
        }
    }

    /**
     * Describe failure message when a test fails.
     *
     * @param description failure message description
     */
    @Override
    public void describeTo(Description description) {
        description.appendText("loose habits match <" + habits + ">");
    }
}

