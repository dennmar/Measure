package com.example.measure.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * Task data access object interacting with the Room database.
 */
@Dao
@TypeConverters({RoomTaskConverters.class})
public interface RoomTaskDao {
    /**
     * Find all tasks within a date range in ascending order by date.
     *
     * @param userId    id of the user the task belongs to
     * @param startDate starting date of the tasks to fetch (inclusive)
     * @param endDate   ending date of the tasks to fetch (exclusive)
     * @return observable list of tasks from the Room database
     */
    // TODO: fix * to specify columns
    @Query("SELECT * FROM tasks WHERE user_id == :userId"
            + " AND local_due_date >= :startDate"
            + " AND local_due_date < :endDate ORDER BY local_due_date ASC")
    LiveData<List<RoomTask>> getSortedTasks(int userId, LocalDate startDate,
                                            LocalDate endDate);

    /**
     * Insert a task in the Room database.
     *
     * @param task task to insert
     * @return row id for the inserted item
     */
    @Insert
    long insert(RoomTask task);

    /**
     * Update a task in the Room database.
     *
     * Note: this uses the primary key to find the task to update.
     *
     * @param task task to update
     * @return the amount of rows updated in the database
     */
    @Update
    int update(RoomTask task);

    /**
     * Remove a task from the Room database.
     *
     * Note: this uses the primary key to find the task to delete.
     *
     * @param task task to delete
     * @return the amount of rows removed from the database
     */
    @Delete
    int delete(RoomTask task);
}
