package com.example.measure.di.components;

import com.example.measure.di.MeasureApplication;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.ContextModule;
import com.example.measure.di.modules.test.MockAgendaViewModelModule;
import com.example.measure.di.modules.test.MockHabitTrackerViewModelModule;
import com.example.measure.di.modules.test.MockLoginViewModelModule;
import com.example.measure.di.modules.test.MockRegisterViewModelModule;
import com.example.measure.di.modules.test.TestMeasureRoomDatabaseModule;

import dagger.BindsInstance;
import dagger.Component;

/**
 * A component targeted for habit tracker fragment tests that makes Dagger
 * create a graph of dependencies and exposes objects from the application
 * graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        MockHabitTrackerViewModelModule.class,
        // Below are modules that are needed from extending
        // ApplicationComponent.
        MockAgendaViewModelModule.class,
        MockRegisterViewModelModule.class,
        MockLoginViewModelModule.class,
        ContextModule.class,
        TestMeasureRoomDatabaseModule.class
})
public interface TestHabitTrackerFragmentComponent
        extends ApplicationComponent {
    /**
     * Creates a new TestHabitTrackerFragmentComponent instance each time it is
     * called.
     */
    @Component.Factory
    interface Factory {
        TestHabitTrackerFragmentComponent newAppComponent(
                @BindsInstance MeasureApplication app);
    }
}
