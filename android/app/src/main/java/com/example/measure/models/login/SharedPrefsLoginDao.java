package com.example.measure.models.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.measure.models.data.User;

import javax.inject.Inject;

/**
 * A shared preferences access object for the login session.
 */
public class SharedPrefsLoginDao implements LoginDao {
    SharedPreferences sharedPrefs;

    @Inject
    public SharedPrefsLoginDao(Context appContext) {
        sharedPrefs = appContext.getSharedPreferences("login_session",
                    Context.MODE_PRIVATE);
    }

    @Override
    public User getCurrentUser() {
        String username = sharedPrefs.getString("username", null);
        String password = sharedPrefs.getString("password", null);

        if (username == null || password == null) {
            return null;
        }

        return new User(username, "", password);
    }

    @Override
    public void setCurrentUser(User user) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        String username = user.getUsername();
        String password = user.getPassword();

        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    @Override
    public void clearSession() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
    }
}
