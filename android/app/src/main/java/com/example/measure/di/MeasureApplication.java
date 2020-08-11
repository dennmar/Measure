package com.example.measure.di;

import android.app.Application;

import androidx.annotation.VisibleForTesting;

import com.example.measure.di.components.ApplicationComponent;
import com.example.measure.di.components.DaggerApplicationComponent;

/**
 * The Measure application with a reference to the application dependency
 * graph.
 */
public class MeasureApplication extends Application {
    public ApplicationComponent appComponent =
            DaggerApplicationComponent.factory().newAppComponent(this);

    /**
     * Set the application component to the given component.
     *
     * Note: this is used to easily change components for instrumented
     * tests. For example, fragments in tests might need to inject mock
     * instances which cannot be passed through the constructor. Instead, the
     * app component including the modules providing the mock classes can be
     * substituted in.
     *
     * @param appComponent the Dagger application component
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setAppComponent(ApplicationComponent appComponent) {
        this.appComponent = appComponent;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void clearAllData() {
        this.appComponent.measureRoomDb().clearAllTables();
    }
}
