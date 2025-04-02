package ru.practicum;

import ru.practicum.manager.TaskManager;
import ru.practicum.model.*;

class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(new Task("Уборка", "Убраться в квартире"));
        taskManager.addTask(new Task("Мытье посуды", "Помыть посуду"));
        System.out.println(taskManager.getAllTasks());
        Task tempTask = taskManager.getTask(1);
        tempTask.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(tempTask);
        System.out.println(taskManager.getTask(1));
        taskManager.removeTask(2);
        System.out.println("Задача после удаления - " + taskManager.getTask(2));
        taskManager.cleanAllTasks();
        System.out.println("Задачи после отчистки - " + taskManager.getAllTasks());
        taskManager.addEpic(new Epic("Генеральная", "Генеральная уборка"));
        taskManager.addSubtask(new Subtask("Передвинуть мебель", "Отодвинуть мебель от стен для доступа в труднодоступные места", 3));
        taskManager.addSubtask(new Subtask("Вымыть посуду", "Начнем с посуды", 3));
        System.out.println(taskManager.getEpic(3));
        System.out.println(taskManager.getEpicSubtasks(3));
        Subtask tempSubtask = taskManager.getSubtask(4);
        tempSubtask.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(tempSubtask);
        System.out.println(taskManager.getSubtask(4));
        System.out.println(taskManager.getEpic(3));
        taskManager.removeSubtask(4);
        System.out.println(taskManager.getEpic(3));
        tempSubtask = taskManager.getSubtask(5);
        tempSubtask.setStatus(Status.DONE);
        taskManager.updateSubtask(tempSubtask);
        System.out.println(taskManager.getEpic(3));
        taskManager.cleanAllSubtasks();
        System.out.println("Подзадачи после удаления - " + taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllEpics());
        taskManager.addSubtask(tempSubtask);
        System.out.println(taskManager.getAllEpics());
        System.out.println("Получение эпика подзадачи - " + taskManager.getSubtask(tempSubtask.getId()).getEpicId());
        Epic tempEpic = taskManager.getEpic(3);
        tempEpic.setName("Мыть посуду");
        tempEpic.setDescription("Стало меньше дел");
        System.out.println(taskManager.getEpic(3));
        taskManager.removeEpic(3);
        System.out.println("Эпики после удаления - " + taskManager.getAllEpics());
    }
}