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

@Dao
@TypeConverters({RoomTaskConverters.class})
public interface RoomTaskDao {
    // TODO: fix * to specify columns
    @Query("SELECT * FROM tasks WHERE user_id = :userId"
            + " AND local_due_date >= :startDate"
            + " AND local_due_date < :endDate")
    LiveData<List<RoomTask>> getSortedTasks(int userId, LocalDate startDate,
                                            LocalDate endDate);

    @Insert
    void insert(RoomTask task);

    @Update
    void update(RoomTask task);

    @Delete
    void delete(RoomTask task);
}
