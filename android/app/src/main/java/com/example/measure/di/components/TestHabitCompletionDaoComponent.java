package com.example.measure.di.components;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.di.MeasureApplication;
import com.example.measure.di.Scopes;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.ContextModule;
import com.example.measure.di.modules.prod.HabitCompletionDaoModule;
import com.example.measure.di.modules.prod.HabitDaoModule;
import com.example.measure.di.modules.test.TestMeasureRoomDatabaseModule;
import com.example.measure.models.habit.HabitCompletionDao;
import com.example.measure.models.habit.HabitDao;

import dagger.BindsInstance;
import dagger.Component;

/**
 * A component targeted for habit completion DAO tests that makes Dagger create
 * a graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        ContextModule.class,
        HabitDaoModule.class,
        HabitCompletionDaoModule.class,
        TestMeasureRoomDatabaseModule.class
})
@Scopes.ApplicationScope
public interface TestHabitCompletionDaoComponent {
    HabitCompletionDao habitCompletionDao();
    HabitDao habitDao();
    MeasureRoomDatabase measureRoomDb();

    /**
     * Creates a new TestHabitCompletionDaoComponent instance each time it is
     * called.
     */
    @Component.Factory
    interface Factory {
        TestHabitCompletionDaoComponent newAppComponent(
                @BindsInstance MeasureApplication app);
    }
}
