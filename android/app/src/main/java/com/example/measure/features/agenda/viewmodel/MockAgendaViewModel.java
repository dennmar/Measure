package com.example.measure.features.agenda.viewmodel;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.models.data.Task;
import com.example.measure.utils.SortByDate;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A fake view model that handles the interaction between the agenda view
 * (fragment) and the model.
 */
public class MockAgendaViewModel implements AgendaViewModel {
    private MutableLiveData<List<Task>> sortedTasks;
    private MutableLiveData<Task> activeTask;
    private List<Task> allTasks;
    private Comparator<Task> sortByDate;


    /**
     * Initialize member variables.
     *
     * @param savedInstanceState previously saved state of the view model
     */
    private MockAgendaViewModel(Bundle savedInstanceState) {
        sortedTasks = new MutableLiveData<>(new ArrayList<>());
        activeTask = new MutableLiveData<>(null);
        allTasks = new ArrayList<>();
        sortByDate = new SortByDate();
    }

    /**
     * Factory for creating a mock agenda view model.
     *
     * Note: this abstraction is only used to comply with the AgendaViewModel
     * interface (which in turn is needed to properly instantiate
     * DaggerAgendaViewModel using assisted injection).
     */
    public static class Factory implements AgendaViewModel.Factory {
        /**
         * Create an instance of a mock agenda view model with the given saved
         * instance state.
         *
         * @param savedInstanceState previously saved state of the view model
         * @return the created agenda view model
         */
        public MockAgendaViewModel create(Bundle savedInstanceState) {
            return new MockAgendaViewModel(savedInstanceState);
        }
    }

    /**
     * Retrieve tasks for the user within a date range in ascending order by
     * date.
     *
     * @param startDate starting date of the agenda (inclusive)
     * @param endDate   ending date of the agenda (exclusive)
     * @return observable list of tasks for the user sorted by date
     */
    @Override
    public LiveData<List<Task>> getSortedTasks(LocalDate startDate,
                                               LocalDate endDate) {
        List<Task> sortedTaskList = new ArrayList<>();
        Collections.sort(allTasks, sortByDate);

        for (Task t : allTasks) {
            if (t.getLocalDueDate().compareTo(startDate) >= 0
                    && t.getLocalDueDate().compareTo(endDate) < 0) {
                sortedTaskList.add(t);
            }
            else if (t.getLocalDueDate().compareTo(endDate) >= 0) {
                break;
            }
        }

        sortedTasks.setValue(sortedTaskList);
        return sortedTasks;
    }

    /**
     * Add a task for the user.
     *
     * @param newTask the task to add
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public boolean addTask(Task newTask) {
        allTasks.add(newTask);
        return true;
    }

    /**
     * Update a task for the user.
     *
     * @param updatedTask the modified task to save
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public boolean updateTask(Task updatedTask) {
        for (int i = 0; i < allTasks.size(); i++) {
            if (allTasks.get(i).getId() == updatedTask.getId()) {
                allTasks.set(i, updatedTask);
                return true;
            }
        }

        return false;
    }

    /**
     * Delete a task for the user.
     *
     * @param task the task to delete
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public boolean deleteTask(Task task) {
        for (int i = 0; i < allTasks.size(); i++) {
            if (allTasks.get(i).getId() == task.getId()) {
                allTasks.remove(i);
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieve the active task for the user.
     *
     * @return current active task for the user
     */
    @Override
    public LiveData<Task> getActiveTask() {
        return activeTask;
    }

    /**
     * Update the active task for the user.
     *
     * @param  task the task to be active
     * @return true if the operation was successful; false otherwise
     */
    @Override
    public boolean updateActiveTask(Task task) {
        activeTask.setValue(task);
        return true;
    }

    /**
     * Perform clean-up for this instance.
     */
    @Override
    public void onDestroy() { }
}
