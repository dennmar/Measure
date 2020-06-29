package com.example.measure.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * A Room database that stores data for the Measure application.
 */
@Database(entities = {RoomTask.class}, version = 1)
public abstract class MeasureRoomDatabase extends RoomDatabase {
    public abstract RoomTaskDao taskDao();
}
