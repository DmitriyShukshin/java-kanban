package ru.practicum.manager;

import ru.practicum.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private int idCounter = 0;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Subtask> subtasks;
    private final Map<Integer, Epic> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void cleanAllTasks() {
        tasks.clear();
    }

    public void cleanAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.clearAllSubtasks();
            checkEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    public void cleanAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public int addTask(Task task) {
        if (task != null) {
            task.setId(calcIdCounter());
            tasks.put(task.getId(), task);
            return idCounter;
        } else return 0;
    }

    public int addSubtask(Subtask subtask) {
        if (subtask == null) {
            return 0;
        }
        int epicId = subtask.getEpicId();
        if (epics.containsKey(epicId)) {
            subtask.setId(calcIdCounter());
            subtasks.put(subtask.getId(), subtask);
            epics.get(epicId).addSubtask(subtask);
            checkEpicStatus(epicId);
            return idCounter;
        } else {
            return 0;
        }
    }

    public int addEpic(Epic epic) {
        if (epic != null) {
            epic.setId(calcIdCounter());
            epics.put(epic.getId(), epic);
            return idCounter;
        } else {
            return 0;
        }
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            checkEpicStatus(subtask.getEpicId());
        }
    }

    public void updateEpic(Epic epic) {
        Epic updatedEpic = epics.get(epic.getId());
        if (updatedEpic == null) {
            return;
        }
        updatedEpic.setName(epic.getName());
        updatedEpic.setDescription(epic.getDescription());
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Subtask tempSubtask = subtasks.get(id);
            subtasks.remove(id);
            epics.get(tempSubtask.getEpicId()).removeSubtask(id);
            checkEpicStatus(tempSubtask.getEpicId());
        }
    }

    public void removeEpic(int id) {
        Epic removedEpic = epics.get(id);
        if (removedEpic == null) return;
        for (int subtaskId : removedEpic.getSubtasksList()) {
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }


    public List<Subtask> getEpicSubtasks(int epicId) {
        if (epics.containsKey(epicId)) {
            List<Subtask> returningSubtasks = new ArrayList<>();
            for (int subtaskId : epics.get(epicId).getSubtasksList()) {
                returningSubtasks.add(subtasks.get(subtaskId));
            }
            return returningSubtasks;
        } else return null;
    }

    private void checkEpicStatus(int id) {
        if (epics.containsKey(id)) {
            boolean isNew = true;
            boolean isDone = true;
            Epic tempEpic = epics.get(id);
            if (tempEpic.getSubtasksList().isEmpty()){
                tempEpic.setStatus(Status.NEW);
                return;
            }
            for (Subtask subtask : getEpicSubtasks(id)) {
                Status subtaskStatus = subtask.getStatus();
                if (subtaskStatus == Status.IN_PROGRESS) {
                    tempEpic.setStatus(Status.IN_PROGRESS);
                    return;
                } else if (subtaskStatus == Status.NEW)
                    isDone = false;
                if (subtaskStatus == Status.DONE)
                    isNew = false;
            }
            if (isNew)
                tempEpic.setStatus(Status.NEW);
            else if (isDone)
                tempEpic.setStatus(Status.DONE);
            else tempEpic.setStatus(Status.IN_PROGRESS);
        }
    }

    private int calcIdCounter() {
        return ++idCounter;
    }
}