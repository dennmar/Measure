package com.example.measure;

import java.util.Date;
import java.util.List;

public class MockTaskRepository implements TaskRepository {
    @Override
    public List<Task> getSortedTasks(User user, Date startDate, Date endDate) {
        return null;
    }
}
