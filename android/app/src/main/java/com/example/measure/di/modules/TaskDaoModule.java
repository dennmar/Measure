package com.example.measure.di.modules;

import android.app.Application;
import android.content.Context;

import com.example.measure.di.MeasureApplication;
import com.example.measure.models.task.SQLiteTaskDao;
import com.example.measure.models.task.TaskDao;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the task DAO.
 */
@Module
public class TaskDaoModule {
    /**
     * Return the task DAO to be provided for all dependents.
     *
     * @param sqliteTaskDao task DAO that interacts with SQlite database
     * @return task DAO to be used for task database access
     */
    @Provides
    public TaskDao provideTaskDao(SQLiteTaskDao sqliteTaskDao) {
        return sqliteTaskDao;
    }
}
