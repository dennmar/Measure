package com.example.measure.di.components;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.modules.AgendaViewModelModule;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.ContextModule;
import com.example.measure.di.modules.LoginRepositoryModule;
import com.example.measure.di.modules.MeasureRoomDatabaseModule;
import com.example.measure.di.modules.TaskDaoModule;
import com.example.measure.di.modules.TaskRepositoryModule;
import com.example.measure.di.modules.UserRepositoryModule;
import com.example.measure.features.agenda.view.AgendaFragment;
import com.example.measure.models.login.LoginDao;

import dagger.BindsInstance;
import dagger.Component;

/**
 * A component that makes Dagger create a graph of dependencies and exposes
 * objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        AgendaViewModelModule.class,
        TaskRepositoryModule.class,
        TaskDaoModule.class,
        LoginRepositoryModule.class,
        LoginDao.class,
        UserRepositoryModule.class,
        ContextModule.class,
        MeasureRoomDatabaseModule.class
})
public interface ApplicationComponent {
    /**
     * Tells dagger that agenda fragment requests injection.
     *
     * @param agendaFragment fragment needing injection
     */
    void inject(AgendaFragment agendaFragment);

    /**
     * Creates a new ApplicationComponent instance each time it is called.
     */
    @Component.Factory
    interface Factory {
        ApplicationComponent newAppComponent(
                @BindsInstance MeasureApplication app);
    }
}
