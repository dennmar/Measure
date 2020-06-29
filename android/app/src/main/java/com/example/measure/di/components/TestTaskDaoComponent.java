package com.example.measure.di.components;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.ContextModule;
import com.example.measure.di.modules.MeasureRoomDatabaseModule;
import com.example.measure.di.modules.TaskDaoModule;
import com.example.measure.models.task.TaskDao;

import dagger.BindsInstance;
import dagger.Component;

/**
 * A component targeted for task DAO tests that makes Dagger create a graph of
 * dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        TaskDaoModule.class,
        ContextModule.class,
        MeasureRoomDatabaseModule.class
})
public interface TestTaskDaoComponent {
    TaskDao taskDao();

    /**
     * Creates a new TestTaskDaoComponent instance each time it is called.
     */
    @Component.Factory
    interface Factory {
        TestTaskDaoComponent newAppComponent(
                @BindsInstance MeasureApplication app);
    }
}
