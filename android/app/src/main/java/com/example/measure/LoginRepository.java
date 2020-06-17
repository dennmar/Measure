package com.example.measure;

/**
 * A repository for accessing the login session data.
 */
public interface LoginRepository {
    /**
     * Retrieve the current user logged in.
     *
     * @return the user currently logged in
     */
    User getCurrentUser();

    /**
     * Set the current user.
     *
     * @param user current user to set
     * @return true if the operation was successful; false otherwise
     */
    boolean setCurrentUser(User user);
}
