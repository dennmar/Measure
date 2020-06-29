package com.example.measure.di.components;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.ContextModule;
import com.example.measure.di.modules.LoginRepositoryModule;
import com.example.measure.di.modules.MeasureRoomDatabaseModule;
import com.example.measure.di.modules.TaskDaoModule;
import com.example.measure.di.modules.TaskRepositoryModule;
import com.example.measure.di.modules.UserRepositoryModule;
import com.example.measure.features.agenda.AgendaViewModel;

import dagger.BindsInstance;
import dagger.Component;

/**
 * A component that makes Dagger create a graph of dependencies and exposes
 * objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        TaskRepositoryModule.class,
        TaskDaoModule.class,
        LoginRepositoryModule.class,
        UserRepositoryModule.class,
        ContextModule.class,
        MeasureRoomDatabaseModule.class
})
public interface ApplicationComponent {
    AgendaViewModel.Factory avmFactory();

    /**
     * Creates a new ApplicationComponent instance each time it is called.
     */
    @Component.Factory
    interface Factory {
        ApplicationComponent newAppComponent(
                @BindsInstance MeasureApplication app);
    }
}
