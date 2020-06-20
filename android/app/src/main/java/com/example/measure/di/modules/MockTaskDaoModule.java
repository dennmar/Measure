package com.example.measure.di.modules;

import com.example.measure.models.task.MockTaskDao;
import com.example.measure.models.task.TaskDao;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide a fake task DAO.
 */
@Module
public class MockTaskDaoModule {
    /**
     * Return the task DAO to be provided for all dependents.
     *
     * @param mockTaskDao fake task DAO
     * @return task DAO to be used for task database access
     */
    @Provides
    public TaskDao provideTaskDao(MockTaskDao mockTaskDao) {
        return mockTaskDao;
    }
}
