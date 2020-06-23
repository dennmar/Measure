package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.TaskDaoModule;
import com.example.measure.models.task.TaskDao;

import dagger.Component;

/**
 * A component targeted for task DAO tests that makes Dagger create a graph of
 * dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        TaskDaoModule.class
})
public interface TestTaskDaoComponent {
    TaskDao taskDao();
}