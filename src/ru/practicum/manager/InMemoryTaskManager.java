package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int idCounter = 0;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Subtask> subtasks;
    private final Map<Integer, Epic> epics;
    private final HistoryManager history;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        history = Managers.getDefaultHistory();
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void cleanAllTasks() {
        tasks.clear();
    }

    @Override
    public void cleanAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.clearAllSubtasks();
            checkEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    @Override
    public void cleanAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        history.add(task);
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        history.add(subtask);
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        history.add(epic);
        return epic;
    }

    @Override
    public int addNewTask(Task task) {
        if (task != null) {
            task.setId(calcIdCounter());
            tasks.put(idCounter, task);
            return idCounter;
        } else return 0;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        if (subtask == null) {
            return 0;
        }
        int epicId = subtask.getEpicId();
        if (epics.containsKey(epicId)) {
            subtask.setId(calcIdCounter());
            subtasks.put(idCounter, subtask);
            epics.get(epicId).addSubtask(subtask);
            checkEpicStatus(epicId);
            return idCounter;
        } else {
            return 0;
        }
    }

    @Override
    public int addEpic(Epic epic) {
        if (epic != null) {
            epic.setId(calcIdCounter());
            epics.put(idCounter, epic);
            return idCounter;
        } else {
            return 0;
        }
    }

    @Override
    public void updateTask(Task task) {
        Task updatedTask = tasks.get(task.getId());
        if (updatedTask != null) {
            updatedTask.setName(task.getName());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setStatus(task.getStatus());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        Subtask updatedSubtask = subtasks.get(subtask.getId());
        if (updatedSubtask != null) {
            updatedSubtask.setName(subtask.getName());
            updatedSubtask.setDescription(subtask.getDescription());
            updatedSubtask.setStatus(subtask.getStatus());
            checkEpicStatus(subtask.getEpicId());
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic updatedEpic = epics.get(epic.getId());
        if (updatedEpic == null) {
            return;
        }
        updatedEpic.setName(epic.getName());
        updatedEpic.setDescription(epic.getDescription());
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Subtask tempSubtask = subtasks.get(id);
            subtasks.remove(id);
            epics.get(tempSubtask.getEpicId()).removeSubtask(id);
            checkEpicStatus(tempSubtask.getEpicId());
        }
    }

    @Override
    public void removeEpic(int id) {
        Epic removedEpic = epics.get(id);
        if (removedEpic == null) return;
        for (int subtaskId : removedEpic.getSubtasks()) {
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }


    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        if (epics.containsKey(epicId)) {
            List<Subtask> returningSubtasks = new ArrayList<>();
            for (int subtaskId : epics.get(epicId).getSubtasks()) {
                returningSubtasks.add(subtasks.get(subtaskId));
            }
            return returningSubtasks;
        } else return null;
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    private void checkEpicStatus(int id) {
        if (epics.containsKey(id)) {
            boolean isNew = true;
            boolean isDone = true;
            Epic tempEpic = epics.get(id);
            if (tempEpic.getSubtasks().isEmpty()) {
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