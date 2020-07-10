package com.example.measure.features.agenda;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.features.ViewModel;
import com.example.measure.models.data.Task;
import com.example.measure.models.data.User;
import com.example.measure.models.login.LoginRepository;
import com.example.measure.models.task.TaskRepository;
import com.example.measure.models.user.UserRepository;
import com.example.measure.utils.DBOperationException;
import com.example.measure.utils.InvalidQueryException;
import com.squareup.inject.assisted.Assisted;
import com.squareup.inject.assisted.AssistedInject;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * A view model that handles the interaction between the agenda view (fragment)
 * and the model, using Dagger to satisfy dependencies.
 */
public class DaggerAgendaViewModel extends ViewModel
        implements AgendaViewModel {
    private TaskRepository taskRepo;
    private LoginRepository loginRepo;
    private UserRepository userRepo;
    private Bundle savedInstanceState;

    private User currUser;
    private MutableLiveData<List<Task>> sortedTasks;
    private MutableLiveData<Task> activeTask;

    /**
     * Initialize all repositories for data access and the saved instance
     * state.
     *
     * @param taskRepo           repository for accessing task data
     * @param loginRepo          repository for accessing login data
     * @param userRepo           repository for accessing user data
     * @param savedInstanceState previously saved state of the view model
     */
    @AssistedInject
    public DaggerAgendaViewModel(TaskRepository taskRepo,
            LoginRepository loginRepo, UserRepository userRepo,
            @Assisted Bundle savedInstanceState) {
        this.taskRepo = taskRepo;
        this.savedInstanceState = savedInstanceState;
        this.loginRepo = loginRepo;
        this.userRepo = userRepo;

        currUser = this.loginRepo.getCurrentUser();
        sortedTasks = new MutableLiveData<>();
        activeTask = new MutableLiveData<>();
    }

    /**
     * Factory for creating a dagger agenda view model using assisted
     * injection.
     */
    @AssistedInject.Factory
    public interface Factory extends AgendaViewModel.Factory {
        /**
         * Create an instance of a dagger agenda view model with the given
         * saved instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created agenda view model
         */
        DaggerAgendaViewModel create(Bundle savedInstanceState);
    }

    /**
     * Retrieve tasks for the user within a date range in ascending order by
     * date.
     *
     * @param startDate starting date of the agenda (inclusive)
     * @param endDate   ending date of the agenda (exclusive)
     * @return observable list of tasks for the user sorted by date
     */
    public LiveData<List<Task>> getSortedTasks(LocalDate startDate,
                                               LocalDate endDate) {
        enableObservers();

        try {
            taskRepo.getSortedTasks(currUser, startDate, endDate).observe(this,
                    modelSortedTasks -> {
                        sortedTasks.setValue(modelSortedTasks);
                    });
        }
        catch (DBOperationException dboe) {
            // TODO: handle exception
        }
        catch (InvalidQueryException iqe) {

        }
        finally {
            return sortedTasks;
        }
    }

    /**
     * Add a task for the user.
     *
     * @param newTask the task to add
     * @return true if the operation was successful; false otherwise
     */
    public boolean addTask(Task newTask) {
        try {
            boolean success = taskRepo.addTask(currUser, newTask);
            return success;
        }
        catch (DBOperationException e) {
            // TODO: handle exception
            return false;
        }
        catch (InvalidQueryException iqe) {
            // TODO: handle exception
            return false;
        }
    }

    /**
     * Update a task for the user.
     *
     * @param updatedTask the modified task to save
     * @return true if the operation was successful; false otherwise
     */
    public boolean updateTask(Task updatedTask) {
        try {
            boolean success = taskRepo.updateTask(currUser, updatedTask);
            return success;
        }
        catch (DBOperationException e) {
            // TODO: handle exception
            return false;
        }
        catch (InvalidQueryException iqe) {
            // TODO: handle exception
            return false;
        }
    }

    /**
     * Delete a task for the user.
     *
     * @param task the task to delete
     * @return true if the operation was successful; false otherwise
     */
    public boolean deleteTask(Task task) {
        try {
            boolean success = taskRepo.deleteTask(currUser, task);
            return success;
        }
        catch (DBOperationException e) {
            return false;
        }
        catch (InvalidQueryException iqe) {
            // TODO: handle exception
            return false;
        }
    }

    /**
     * Retrieve the active task for the user.
     *
     * @return current active task for the user
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public LiveData<Task> getActiveTask() {
        enableObservers();

        userRepo.getActiveTask().observe(this, activeTask -> {
            this.activeTask.setValue(activeTask);
        });

        return activeTask;
    }

    /**
     * Update the active task for the user.
     *
     * @param  task the task to be active
     * @return true if the operation was successful; false otherwise
     */
    public boolean updateActiveTask(Task task) {
        boolean success = userRepo.updateActiveTask(task);
        return success;
    }

    /**
     * Perform clean-up for this instance.
     */
    public void onDestroy() {
        removeObservers();
    }
}
