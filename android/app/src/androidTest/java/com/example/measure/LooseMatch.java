package com.example.measure;

import com.example.measure.models.data.Task;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

/**
 * Check if a task loosely matches another task (ignoring their id numbers).
 */
public class LooseMatch extends TypeSafeMatcher<List<Task>> {
    private List<Task> tasks;

    /**
     * Initialize member variables.
     *
     * @param tasks list of tasks to be used for comparison
     */
    public LooseMatch(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Return a matcher with the given task to check for a loose match.
     *
     * @param tasks list of tasks to be used for comparison
     * @return a matcher for task that checks for a loose match
     */
    public static Matcher<List<Task>> looseMatch(List<Task> tasks) {
        return new LooseMatch(tasks);
    }

    /**
     * Check if the given list of task loosely matches (only ignoring task id
     * but checking for equality on fields and order).
     *
     * @param otherTasks list of tasks to be compared
     * @return true if the list of tasks loosely matches; false otherwise
     */
    @Override
    protected boolean matchesSafely(List<Task> otherTasks) {
        if (tasks.size() != otherTasks.size()) {
            return false;
        }

        for (int i = 0; i < tasks.size(); i++) {
            if (!tasksLooselyMatch(tasks.get(i), otherTasks.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if the two tasks loosely match (only ignoring task id).
     *
     * @param t1 first task to be compared
     * @param t2 second task to be compared
     * @return true if the tasks loosely match; false otherwise
     */
    private boolean tasksLooselyMatch(Task t1, Task t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        else if (t1 != null && t2 != null) {
            if (t1.name == null && t2.name != null) {
                return false;
            }
            else if (t1.name != null && !t1.name.equals(t2.name)) {
                return false;
            }

            if (t1.timeWorked == null && t2.timeWorked != null) {
                return false;
            }
            else if (t1.timeWorked != null
                    && !t1.timeWorked.equals(t2.timeWorked)) {
                return false;
            }

            if (t1.localDueDate == null && t2.localDueDate != null) {
                return false;
            }
            else if (t1.localDueDate != null
                    && !t1.localDueDate.equals(t2.localDueDate)) {
                return false;
            }

            return t1.userId == t2.userId
                    && t1.isCompleted == t2.isCompleted;
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
        description.appendText("loose match <" + tasks + ">");
    }
}
