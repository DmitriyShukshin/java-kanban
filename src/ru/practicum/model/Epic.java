package ru.practicum.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
        this.subtasks = new ArrayList<>();
    }

    public Epic(String name, String description, int id, List<Integer> subtasks) {
        super(name, description, id);
        this.subtasks = subtasks;
    }

    public Epic(Epic epic) {
        super(epic);
        this.subtasks = epic.getSubtasks();
    }

    public List<Integer> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask.getId());
    }

    public void removeSubtask(Integer id) {
        subtasks.remove(id);
    }

    public void clearAllSubtasks() {
        subtasks.clear();
    }

    @Override
    public String toString() {
        return this.getClass() + "{\n" +
                "   ID = " + id + '\n' +
                "   subtasks = " + subtasks + '\n' +
                "   name = " + name + '\n' +
                "   description = " + description + '\n' +
                "   status = " + status + '\n' +
                "}\n";
    }
}