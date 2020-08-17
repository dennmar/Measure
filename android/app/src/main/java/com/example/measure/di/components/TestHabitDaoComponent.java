package com.example.measure.di.components;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.di.MeasureApplication;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.ContextModule;
import com.example.measure.di.modules.prod.HabitDaoModule;
import com.example.measure.di.modules.test.TestMeasureRoomDatabaseModule;
import com.example.measure.models.habit.HabitDao;

import dagger.BindsInstance;
import dagger.Component;

/**
 * A component targeted for habit DAO tests that makes Dagger create a graph of
 * dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        HabitDaoModule.class,
        ContextModule.class,
        TestMeasureRoomDatabaseModule.class
})
public interface TestHabitDaoComponent {
    HabitDao habitDao();
    MeasureRoomDatabase measureRoomDb();

    /**
     * Creates a new TestHabitDaoComponent instance each time it is called.
     */
    @Component.Factory
    interface Factory {
        TestHabitDaoComponent newAppComponent(
                @BindsInstance MeasureApplication app);
    }
}
