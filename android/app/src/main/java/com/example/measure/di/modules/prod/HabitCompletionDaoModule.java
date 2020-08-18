package com.example.measure.di.modules.prod;

import com.example.measure.models.habit.HabitCompletionDao;
import com.example.measure.models.habit.SQLiteHabitCompletionDao;

import dagger.Module;
import dagger.Provides;

/**
 * A Dagger module that specifies how to provide the habit completion DAO.
 */
@Module
public class HabitCompletionDaoModule {
    /**
     * Return the habit completion DAO to be provided for all dependents.
     *
     * @param sqLiteHabitCompletionDao habit completion DAO that interacts
     *                                 with SQLite database
     * @return habit completion DAO to be used for habit completion
     * database access
     */
    @Provides
    public HabitCompletionDao provideHabitCompletionDao(
            SQLiteHabitCompletionDao sqLiteHabitCompletionDao) {
        return sqLiteHabitCompletionDao;
    }
}
