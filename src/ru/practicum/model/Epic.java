package ru.practicum.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksList;

    public Epic(String name, String description) {
        super(name, description);
        subtasksList = new ArrayList<>();
    }

    public List<Integer> getSubtasksList() {
        return subtasksList;
    }

    public void addSubtask(Subtask subtask) {
        subtasksList.add(subtask.getId());
    }

    public void removeSubtask(Integer id) {
        subtasksList.remove(id);
    }

    public void clearAllSubtasks() {
        subtasksList.clear();
    }

    @Override
    public String toString() {
        return this.getClass() + "{\n" +
                "   ID = " + id + '\n' +
                "   subtasks = " + subtasksList.toString() + '\n' +
                "   name = " + name + '\n' +
                "   description = " + description + '\n' +
                "   status = " + status + '\n' +
                "}\n";
    }
}