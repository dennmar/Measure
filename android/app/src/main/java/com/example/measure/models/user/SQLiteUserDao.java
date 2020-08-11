package com.example.measure.models.user;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.db.RoomUser;
import com.example.measure.db.RoomUserDao;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

/**
 * A SQLite user database access object.
 */
public class SQLiteUserDao implements UserDao {
    private RoomUserDao roomUserDao;
    private MutableLiveData<User> foundUser;
    private ExecutorService execService;

    /**
     * Initialize member variables.
     *
     * @param db SQLite database where users are stored
     */
    @Inject
    public SQLiteUserDao(MeasureRoomDatabase db) {
        roomUserDao = db.userDao();
        foundUser = new MutableLiveData<>(null);
        execService = Executors.newFixedThreadPool(1);
    }

    /**
     * Add a user to the database.
     *
     * @param user user to be added
     * @throws DBOperationException if the user could not be added
     */
    @Override
    public void asyncAddUser(User user) throws DBOperationException {
        RoomUser roomUser = new RoomUser(user);

        try {
            Callable<Void> asyncInsertUser = () -> {
                roomUserDao.insert(roomUser);
                return null;
            };

            Future<Void> insertUser = execService.submit(asyncInsertUser);
            insertUser.get();
        }
        catch (Exception e) {
            String errMsg = e.getMessage();
            Log.d("SQLiteUserDAO", "ERROR: " + errMsg);

            if (errMsg.contains("UNIQUE constraint failed: users.username")) {
                throw new DBOperationException("Username is not available.");
            }
            else if (errMsg.contains("UNIQUE constraint failed: users.email")) {
                throw new DBOperationException("Email address is not available.");
            }

            throw new DBOperationException(e.getMessage());
        }
    }

    /**
     * Retrieve a user from the database with the matching username and
     * password.
     *
     * @param username username of the user to fetch
     * @param password password of the user to fetch
     * @return the matching user or null if no matching user was found
     * @throws DBOperationException if the user could not be fetched
     */
    @Override
    public User asyncGetUser(String username, String password)
            throws DBOperationException {
        try {
            Callable<User> asyncFindUser = () -> {
                RoomUser foundUser = roomUserDao.getUser(username, password);

                if (foundUser != null) {
                    return foundUser.toUser();
                }

                return null;
            };

            Future<User> foundUser = execService.submit(asyncFindUser);
            return foundUser.get();
        }
        catch (Exception e) {
            throw new DBOperationException(e.getMessage());
        }
    }
}
