package com.example.measure.di.components;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.ContextModule;
import com.example.measure.di.modules.prod.LoginDaoModule;
import com.example.measure.models.login.LoginDao;

import dagger.BindsInstance;
import dagger.Component;

/**
 * A component targeted for login DAO tests that makes Dagger create a graph of
 * dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        LoginDaoModule.class,
        ContextModule.class
})
public interface TestLoginDaoComponent {
    LoginDao loginDao();

    /**
     * Creates a new TestLoginDaoComponent instance each time it is called.
     */
    @Component.Factory
    interface Factory {
        TestLoginDaoComponent newAppComponent(
                @BindsInstance MeasureApplication app);
    }
}
