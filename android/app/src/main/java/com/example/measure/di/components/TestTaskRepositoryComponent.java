package com.example.measure.di.components;

import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.MockTaskDaoModule;
import com.example.measure.di.modules.TaskRepositoryModule;
import com.example.measure.models.task.TaskRepository;

import dagger.Component;

/**
 * A component targeted for task repository tests that makes Dagger create a
 * graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        TaskRepositoryModule.class,
        MockTaskDaoModule.class
})
public interface TestTaskRepositoryComponent {
    TaskRepository taskRepository();
}
