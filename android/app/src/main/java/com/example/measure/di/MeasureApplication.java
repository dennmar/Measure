package com.example.measure.di;

import android.app.Application;

import com.example.measure.di.components.ApplicationComponent;
import com.example.measure.di.components.DaggerApplicationComponent;

/**
 * The Measure application with a reference to the application dependency
 * graph.
 */
public class MeasureApplication extends Application {
    public ApplicationComponent appComponent =
            DaggerApplicationComponent.create();
}
