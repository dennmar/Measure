package com.example.measure.di.modules.prod;

import com.example.measure.models.task.LocalTaskRepository;
import com.example.measure.models.task.TaskRepository;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the task repository.
 */
@Module
public class TaskRepositoryModule {
    /**
     * Return the task repository to be provided for all dependents.
     *
     * @param localTaskRepo task repository using local database
     * @return task repository to be used for task data access
     */
    @Provides
    public TaskRepository provideTaskRepository(
            LocalTaskRepository localTaskRepo) {
        return localTaskRepo;
    }
}
