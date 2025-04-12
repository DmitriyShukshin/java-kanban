package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final byte HISTORY_LIMIT = 10;
    private final List<Task> history;

    public InMemoryHistoryManager() {
        this.history = new LinkedList<>();
    }

    @Override
    public List<Task> getHistory() {
        return  new ArrayList<>(history);
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        Task newTask;
        if (task instanceof Epic epic){
            newTask = new Epic(epic);
        } else if (task instanceof Subtask subtask) {
            newTask = new Subtask(subtask);
        } else {
            newTask = new Task(task);
        }
        if (history.size() >= HISTORY_LIMIT) {
            history.removeFirst();
        }
        history.add(newTask);
    }
}
