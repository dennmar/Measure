package com.example.measure.di;

import com.example.measure.LocalTaskRepository;
import com.example.measure.MockTaskRepository;
import com.example.measure.TaskRepository;

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
     * @return task repository to be used for task data access
     */
    @Provides
    public TaskRepository provideTaskRepository() {
        return new MockTaskRepository();
    }
}
