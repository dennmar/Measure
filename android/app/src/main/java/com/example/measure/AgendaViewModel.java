package com.example.measure;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.squareup.inject.assisted.Assisted;
import com.squareup.inject.assisted.AssistedInject;

import java.util.Date;
import java.util.List;

/**
 * A view model that handles the interaction between the agenda view (fragment)
 * and the model.
 */
public class AgendaViewModel {
    private final TaskRepository taskRepo;
    private final LoginRepository loginRepo;
    private final Bundle savedInstanceState;
    private User currUser;
    private MutableLiveData<List<Task>> sortedTasks;

    /**
     * Initialize all repositories for data access and the saved instance
     * state.
     *
     * @param taskRepo           repository for accessing task data
     * @param loginRepo          repository for accessing login data
     * @param savedInstanceState previously saved state of the view model
     */
    @AssistedInject
    public AgendaViewModel(TaskRepository taskRepo, LoginRepository loginRepo,
                           @Assisted Bundle savedInstanceState) {
        this.taskRepo = taskRepo;
        this.savedInstanceState = savedInstanceState;
        this.loginRepo = loginRepo;

        currUser = this.loginRepo.getCurrentUser();
        sortedTasks = new MutableLiveData<>();
    }

    /**
     * Factory for creating an agenda view model using assisted injection.
     */
    @AssistedInject.Factory
    public interface Factory {
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
    public LiveData<List<Task>> getSortedTasks(Date startDate, Date endDate) {
        List<Task> sortedTasksList = taskRepo.getSortedTasks(currUser,
                startDate, endDate);
        sortedTasks.setValue(sortedTasksList);
        return sortedTasks;
    }

    /**
     * Add a task for the user.
     *
     * @param newTask the task to add
     * @return true if the operation was successful; false otherwise
     */
    public boolean addTask(Task newTask) {
        boolean success = taskRepo.addTask(currUser, newTask);
        return success;
    }

    /**
     * Update a task for the user.
     *
     * @param updatedTask the modified task to save
     * @return true if the operation was successful; false otherwise
     */
    public boolean updateTask(Task updatedTask) {
        boolean success = taskRepo.updateTask(currUser, updatedTask);
        return success;
    }

    /**
     * Delete a task for the user.
     *
     * @param task the task to delete
     * @return true if the operation was successful; false otherwise
     */
    public boolean deleteTask(Task task) {
        boolean success = taskRepo.deleteTask(currUser, task);
        return success;
    }

    /**
     * Set the active task for the user.
     *
     * @param  activeTask the task to be active
     * @return true if the operation was successful; false otherwise
     */
    public boolean setActiveTask(Task activeTask) {
        return false;
    }
}
