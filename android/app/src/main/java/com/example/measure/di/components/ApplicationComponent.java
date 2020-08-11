package com.example.measure.di.components;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.di.MeasureApplication;
import com.example.measure.di.Scopes;
import com.example.measure.di.modules.prod.AgendaViewModelModule;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.ContextModule;
import com.example.measure.di.modules.prod.LoginDaoModule;
import com.example.measure.di.modules.prod.LoginRepositoryModule;
import com.example.measure.di.modules.prod.LoginViewModelModule;
import com.example.measure.di.modules.prod.MeasureRoomDatabaseModule;
import com.example.measure.di.modules.prod.RegisterViewModelModule;
import com.example.measure.di.modules.prod.TaskDaoModule;
import com.example.measure.di.modules.prod.TaskRepositoryModule;
import com.example.measure.di.modules.prod.UserDaoModule;
import com.example.measure.di.modules.prod.UserRepositoryModule;
import com.example.measure.features.agenda.view.AgendaFragment;
import com.example.measure.features.login.LoginFragment;
import com.example.measure.features.register.RegisterFragment;

import dagger.BindsInstance;
import dagger.Component;

/**
 * A component that makes Dagger create a graph of dependencies and exposes
 * objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        AgendaViewModelModule.class,
        RegisterViewModelModule.class,
        LoginViewModelModule.class,
        TaskRepositoryModule.class,
        TaskDaoModule.class,
        LoginRepositoryModule.class,
        LoginDaoModule.class,
        UserRepositoryModule.class,
        UserDaoModule.class,
        ContextModule.class,
        MeasureRoomDatabaseModule.class
})
@Scopes.ApplicationScope
public interface ApplicationComponent {
    MeasureRoomDatabase measureRoomDb();

    /**
     * Tells Dagger that the agenda fragment requests injection.
     *
     * @param agendaFragment agenda fragment needing injection
     */
    void inject(AgendaFragment agendaFragment);

    /**
     * Tells Dagger that the register fragment requests injection.
     *
     * @param registerFragment register fragment needing injection
     */
    void inject(RegisterFragment registerFragment);

    /**
     * Tells Dagger that the login fragment requests injection.
     *
     * @param loginFragment login fragment needing injection
     */
    void inject(LoginFragment loginFragment);

    /**
     * Creates a new ApplicationComponent instance each time it is called.
     */
    @Component.Factory
    interface Factory {
        ApplicationComponent newAppComponent(
                @BindsInstance MeasureApplication app);
    }
}
