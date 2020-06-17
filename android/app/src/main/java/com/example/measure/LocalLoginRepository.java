package com.example.measure;

/**
 * A repository using a local database for accessing the login session data.
 */
public class LocalLoginRepository implements LoginRepository {
    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public boolean setCurrentUser(User user) {
        return false;
    }
}
