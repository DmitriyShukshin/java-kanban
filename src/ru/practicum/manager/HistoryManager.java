package ru.practicum.manager;

import ru.practicum.model.Task;

import java.util.List;

public interface HistoryManager {
    List<Task> getHistory();
    void add(Task task);
}
