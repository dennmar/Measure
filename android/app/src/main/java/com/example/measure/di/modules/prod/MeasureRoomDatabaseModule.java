package com.example.measure.di.modules.prod;

import android.content.Context;

import androidx.room.Room;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.di.Scopes;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the Measure Room database.
 */
@Module
public class MeasureRoomDatabaseModule {
    /**
     * Return the Measure Room database to be provided to all dependents.
     *
     * @param appContext application context
     * @return the Room database for the application
     */
    @Provides
    @Scopes.ApplicationScope
    public MeasureRoomDatabase provideMeasureRoomDatabase(Context appContext) {
        return Room.databaseBuilder(appContext, MeasureRoomDatabase.class,
                "measure_db").fallbackToDestructiveMigration().build();
    }
}
