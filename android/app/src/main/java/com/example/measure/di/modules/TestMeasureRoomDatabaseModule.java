package com.example.measure.di.modules;

import android.content.Context;

import androidx.room.Room;

import com.example.measure.db.MeasureRoomDatabase;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the test Measure Room
 * database.
 */
@Module
public class TestMeasureRoomDatabaseModule {
    /**
     * Return the test Measure Room database to be provided to all dependents.
     *
     * @param appContext application context
     * @return the test Room database for the application
     */
    @Provides
    public MeasureRoomDatabase provideMeasureRoomDatabase(Context appContext) {
        return Room.databaseBuilder(appContext, MeasureRoomDatabase.class,
                "test_measure_db").fallbackToDestructiveMigration().build();
    }
}