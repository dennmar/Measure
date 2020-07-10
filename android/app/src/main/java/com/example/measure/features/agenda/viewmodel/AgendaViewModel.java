package com.example.measure.features.agenda.viewmodel;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import com.example.measure.models.data.Task;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * A view model that handles the interaction between the agenda view (fragment)
 * and the model.
 */
public interface AgendaViewModel {
    /**
     * Factory for creating an agenda view model.
     */
    interface Factory {
        /**
         * Create an instance of an agenda view model with the given saved
         * instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created agenda view model
         */
        AgendaViewModel create(Bundle savedInstanceState);
    }

    /**
     * Retrieve tasks for the user within a date range in ascending order by
     * date.
     *
     * @param startDate starting date of the agenda (inclusive)
     * @param endDate   ending date of the agenda (exclusive)
     * @return observable list of tasks for the user sorted by date
     */
    LiveData<List<Task>> getSortedTasks(LocalDate startDate,
                                        LocalDate endDate);

    /**
     * Add a task for the user.
     *
     * @param newTask the task to add
     * @return true if the operation was successful; false otherwise
     */
    boolean addTask(Task newTask);

    /**
     * Update a task for the user.
     *
     * @param updatedTask the modified task to save
     * @return true if the operation was successful; false otherwise
     */
    boolean updateTask(Task updatedTask);

    /**
     * Delete a task for the user.
     *
     * @param task the task to delete
     * @return true if the operation was successful; false otherwise
     */
    boolean deleteTask(Task task);

    /**
     * Retrieve the active task for the user.
     *
     * @return current active task for the user
     */
    LiveData<Task> getActiveTask();

    /**
     * Update the active task for the user.
     *
     * @param  task the task to be active
     * @return true if the operation was successful; false otherwise
     */
    boolean updateActiveTask(Task task);

    /**
     * Perform clean-up for this instance.
     */
    void onDestroy();
}
