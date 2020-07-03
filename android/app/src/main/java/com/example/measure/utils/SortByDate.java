package com.example.measure.utils;

import com.example.measure.models.data.Task;

import java.util.Comparator;

/**
 * Sort tasks by date in ascending order.
 */
public class SortByDate implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getLocalDueDate().compareTo(o2.getLocalDueDate());
    }
}
