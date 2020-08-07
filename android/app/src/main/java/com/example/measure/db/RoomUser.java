package com.example.measure.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.measure.models.data.User;

/**
 * A user for the Room database.
 */
@Entity(tableName = "users", indices = {
        @Index(value = "username", unique = true),
        @Index(value = "email", unique = true)
})
public class RoomUser {
    // Should be equal to 0 to allow id to be auto-generated.
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    private String password;

    @ColumnInfo(name = "active_task_id")
    @Nullable
    private Long activeTaskId;

    /**
     * Initialize member variables to default values.
     */
    public RoomUser() {
        id = -1;
        username = null;
        email = null;
        password = null;
        activeTaskId = null;
    }

    /**
     * Initialize member variables from a User.
     *
     * @param user user holding data to initialize the Room user
     */
    public RoomUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();

        if (user.getActiveTask() == null)  {
            this.activeTaskId = null;
        }
        else {
            this.activeTaskId = user.getActiveTask().getId();
        }
    }

    /**
     * Return the User representation of this RoomUser.
     *
     * @return the RoomUser converted to a User
     */
    public User toUser() {
        // TODO: active task needs a relation to be fetched
        return new User(id, username, email, password, null);
    }

    /* Getters and setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public Long getActiveTaskId() {
        return activeTaskId;
    }

    public void setActiveTaskId(Long activeTaskId) {
        this.activeTaskId = activeTaskId;
    }
}
