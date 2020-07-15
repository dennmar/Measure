package com.example.measure.di.modules.test;

import com.example.measure.models.task.MockTaskRepository;
import com.example.measure.models.task.TaskRepository;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake task repository.
 */
@Module
public class MockTaskRepositoryModule {
    /**
     * Return the task repository to be provided for all dependents.
     *
     * @param mockTaskRepo fake task repository
     * @return task repository to be used for task data access
     */
    @Provides
    public TaskRepository provideTaskRepository(
            MockTaskRepository mockTaskRepo) {
        return mockTaskRepo;
    }
}
