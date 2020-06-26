package com.example.measure.di.modules;

import android.content.Context;

import com.example.measure.di.MeasureApplication;

import dagger.Module;
import dagger.Provides;

/**
 * A dagger module that specifies how to provide the application context.
 */
@Module
public class ContextModule {
    /**
     * Return the (application) context to be provided for all dependents.
     *
     * @param app the Measure application
     * @return context to be provided
     */
    @Provides
    public Context provideContext(MeasureApplication app) {
        return app;
    }
}
