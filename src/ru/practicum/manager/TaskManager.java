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
        cleanAllSubtasks();
        epics.clear();
    }

    public Task getTask(int id) {
        if (!tasks.containsKey(id))
            System.out.println("Задача с id - " + id + " не найдена.");
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        if (!subtasks.containsKey(id))
            System.out.println("Подзадача с id - " + id + " не найдена.");
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        if (!epics.containsKey(id))
            System.out.println("Подзадача с id - " + id + " не найдена.");
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
            epics.get(epicId).getSubtasksList().add(subtask.getId());
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
            System.out.println("Задача с id - " + task.getId() + " обновлена.");
        } else {
            System.out.println("Задача с id - " + task.getId() + " не найдена.");
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            checkEpicStatus(subtask.getEpicId());
            System.out.println("Подзадача с id - " + subtask.getId() + " обновлена.");
        } else {
            System.out.println("Подзадача с id - " + subtask.getId() + " не найдена.");
        }
    }

    public void updateEpic(Epic epic) {
        Epic updatedEpic = epics.get(epic.getId());
        if (updatedEpic == null) {
            System.out.println("Эпик с id - " + epic.getId() + " не найдена.");
            return;
        }
        updatedEpic.setName(epic.getName());
        updatedEpic.setDescription(epic.getDescription());
        System.out.println("Эпик с id - " + epic.getId() + " обновлена.");
    }

    public void removeTask(int id) {
        if (tasks.containsKey(id)) {
            System.out.println("Задача \"" + tasks.get(id).getName() + "\" удалена.");
            tasks.remove(id);
        } else {
            System.out.println("Задача с id - " + id + " не найдена.");
        }
    }

    public void removeSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Subtask tempSubtask = subtasks.get(id);
            System.out.println("Подзадача \"" + subtasks.get(id).getName() + "\" удалена.");
            subtasks.remove(id);
            epics.get(tempSubtask.getEpicId()).getSubtasksList().remove(Integer.valueOf(id));
            checkEpicStatus(tempSubtask.getEpicId());
        } else {
            System.out.println("Подзадача с id - " + id + " не найдена.");
        }
    }

    public void removeEpic(int id) {
        Epic removedEpic = epics.get(id);
        if (removedEpic == null) return;
        for (int subtaskId : removedEpic.getSubtasksList()) {
            subtasks.remove(subtaskId);
        }
        removedEpic.clearAllSubtasks();
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
            boolean isNew = false;
            boolean isDone = false;
            Epic tempEpic = epics.get(id);
            if (tempEpic.getSubtasksList().isEmpty())
                isNew = true;
            for (Subtask subtask : getEpicSubtasks(id)) {
                Status subtaskStatus = subtask.getStatus();
                if (subtaskStatus == Status.IN_PROGRESS) {
                    tempEpic.setStatus(Status.IN_PROGRESS);
                    return;
                } else if (subtaskStatus == Status.NEW)
                    isNew = true;
                else if (subtaskStatus == Status.DONE)
                    isDone = true;
            }
            if (isNew && isDone)
                tempEpic.setStatus(Status.IN_PROGRESS);
            else if (isNew && !isDone)
                tempEpic.setStatus(Status.NEW);
            else tempEpic.setStatus(Status.DONE);
        } else {
            System.out.println("Эпик с id - " + id + " не найден.");
        }
    }

    private int calcIdCounter() {
        return ++idCounter;
    }
}