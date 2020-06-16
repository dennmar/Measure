package com.example.measure;

import java.util.Date;
import java.util.List;

/**
 * A repository for accessing the task data.
 */
public interface TaskRepository {
    /**
     * Retrieve all tasks for the user from the database within a date range in
     * ascending order.
     *
     * @param user      user to retrieve tasks for
     * @param startDate starting date of tasks to fetch (inclusive)
     * @param endDate   ending date of tasks to fetch (exclusive)
     * @return a list of tasks belonging to the user sorted by date
     */
    List<Task> getSortedTasks(User user, Date startDate, Date endDate);
}
