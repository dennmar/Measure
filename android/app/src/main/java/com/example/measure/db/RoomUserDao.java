package com.example.measure.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * User data access object interacting with the Room database.
 */
@Dao
public interface RoomUserDao {
    /**
     * Retrieve a user from the database with the matching username and
     * password.
     * @param username username of the user to fetch
     * @param password password of the user to fetch
     * @return the matching user from the Room database or null if no
     *         matching user was found
     */
    // TODO: fix * to specify columns
    @Query("SELECT * FROM users WHERE username = :username "
            + "AND password = :password")
    RoomUser getUser(String username, String password);

    /**
     * Insert a user in the Room database
     *
     * @param user user to insert
     * @return row id for the inserted item
     */
    @Insert
    long insert(RoomUser user);
}
