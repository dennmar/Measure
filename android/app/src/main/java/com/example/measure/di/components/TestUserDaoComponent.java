package com.example.measure.di.components;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.di.MeasureApplication;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.ContextModule;
import com.example.measure.di.modules.prod.UserDaoModule;
import com.example.measure.di.modules.test.TestMeasureRoomDatabaseModule;
import com.example.measure.models.user.UserDao;

import dagger.BindsInstance;
import dagger.Component;

/**
 * A component targeted for user DAO tests that makes Dagger create a graph of
 * dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        UserDaoModule.class,
        ContextModule.class,
        TestMeasureRoomDatabaseModule.class
})
public interface TestUserDaoComponent {
    UserDao userDao();
    MeasureRoomDatabase measureRoomDatabase();

    /**
     * Creates a new TestTaskDaoComponent instance each time it is called.
     */
    @Component.Factory
    interface Factory {
        TestUserDaoComponent newAppComponent(
                @BindsInstance MeasureApplication app);
    }
}
