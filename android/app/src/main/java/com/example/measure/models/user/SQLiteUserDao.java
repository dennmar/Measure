package com.example.measure.models.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.measure.db.MeasureRoomDatabase;
import com.example.measure.db.RoomUser;
import com.example.measure.db.RoomUserDao;
import com.example.measure.models.data.User;
import com.example.measure.utils.DBOperationException;

import javax.inject.Inject;

/**
 * A SQLite user database access object.
 */
public class SQLiteUserDao implements UserDao {
    private RoomUserDao roomUserDao;
    private MutableLiveData<User> foundUser;

    /**
     * Initialize member variables.
     *
     * @param db SQLite database where users are stored
     */
    @Inject
    public SQLiteUserDao(MeasureRoomDatabase db) {
        roomUserDao = db.userDao();
        foundUser = new MutableLiveData<>(null);
    }

    /**
     * Add a user to the database.
     *
     * @param user user to be added
     * @throws DBOperationException if the user could not be added
     */
    @Override
    public void addUser(User user) throws DBOperationException {
        RoomUser roomUser = new RoomUser(user);

        try {
            roomUserDao.insert(roomUser);
        }
        catch (Exception e) {
            throw new DBOperationException(e.getMessage());
        }
    }

    /**
     * Retrieve a user from the database with the matching username and
     * password.
     *
     * @param username username of the user to fetch
     * @param password password of the user to fetch
     * @return observable matching user or null if no matching user was found
     * @throws DBOperationException if the user could not be fetched
     */
    @Override
    public LiveData<User> getUser(String username, String password)
            throws DBOperationException {
        try {
            roomUserDao.getUser(username, password).observeForever(user -> {
                if (user != null) {
                    foundUser.setValue(user.toUser());
                }
                else {
                    foundUser.setValue(null);
                }
            });
        }
        catch (Exception e) {
            throw new DBOperationException(e.getMessage());
        }

        return foundUser;
    }
}
