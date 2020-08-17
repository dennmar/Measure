package com.example.measure.di.modules.prod;

import com.example.measure.models.habit.HabitDao;
import com.example.measure.models.habit.SQLiteHabitDao;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the habit DAO.
 */
@Module
public class HabitDaoModule {
    /**
     * Return the habit DAO to be provided for all dependents.
     *
     * @param sqLiteHabitDao habit DAO that interacts with SQLite database
     * @return habit DAO to be used for habit database access
     */
    @Provides
    public HabitDao provideHabitDao(SQLiteHabitDao sqLiteHabitDao) {
        return sqLiteHabitDao;
    }
}
