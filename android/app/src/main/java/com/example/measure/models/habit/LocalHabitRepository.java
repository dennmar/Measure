package com.example.measure.models.habit;

import javax.inject.Inject;

/**
 * A repository using a local database for accessing the habit data.
 */
public class LocalHabitRepository implements HabitRepository {
    @Inject
    public LocalHabitRepository() {}
}
