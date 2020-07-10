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

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setAppComponent(ApplicationComponent appComponent) {
        this.appComponent = appComponent;
    }
}
