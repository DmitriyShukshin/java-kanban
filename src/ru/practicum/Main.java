package ru.practicum;

import ru.practicum.manager.TaskManager;
import ru.practicum.model.*;

class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        int taskID;
        int epicID;
        int subtaskID;

        Task testTask1 = new Task("Задача 1", "Тестовая задача 1");
        Task testTask2 = new Task("Задача 2", "Тестовая задача 2");
        Task testTask3 = new Task("Задача 3", "Тестовая задача 3");

        taskID = taskManager.addTask(testTask1);

        Epic testEpic1 = new Epic("Эпик 1", "Тестовый эпик 1");
        Epic testEpic2 = new Epic("Эпик 2", "Тестовый эпик 2");
        Epic testEpic3 = new Epic("Эпик 3", "Тестовый эпик 3");

        epicID = taskManager.addEpic(testEpic1);

        Subtask testSubtask1 = new Subtask("Подзадача 1", "Тестовая подзадача 1", epicID);
        Subtask testSubtask2 = new Subtask("Подзадача 2", "Тестовая подзадача 2", epicID);
        Subtask testSubtask3 = new Subtask("Подзадача 3", "Тестовая подзадача 3", epicID);

        System.out.println("\nДобавляем подзадачи в первый эпик");

        taskManager.addSubtask(testSubtask1);
        taskManager.addSubtask(testSubtask2);
        taskManager.addSubtask(testSubtask3);

        epicID = taskManager.addEpic(testEpic2);

        Subtask testSubtask4 = new Subtask("Подзадача 4", "Тестовая подзадача 4", epicID);
        Subtask testSubtask5 = new Subtask("Подзадача 5", "Тестовая подзадача 5", epicID);
        Subtask testSubtask6 = new Subtask("Подзадача 6", "Тестовая подзадача 6", epicID);

        System.out.println("\nДобавляем подзадачи во второй эпик и ставим разные статусы");
        taskManager.addSubtask(testSubtask4);
        subtaskID = taskManager.addSubtask(testSubtask5);
        taskManager.getSubtask(subtaskID).setStatus(Status.IN_PROGRESS);
        subtaskID = taskManager.addSubtask(testSubtask6);
        taskManager.getSubtask(subtaskID).setStatus(Status.DONE);
        taskManager.updateSubtask(testSubtask6);

        epicID = taskManager.addEpic(testEpic3);

        Subtask testSubtask7 = new Subtask("Подзадача 7", "Тестовая подзадача 7", epicID);
        Subtask testSubtask8 = new Subtask("Подзадача 8", "Тестовая подзадача 8", epicID);
        Subtask testSubtask9 = new Subtask("Подзадача 9", "Тестовая подзадача 9", epicID);

        System.out.println("\nДобавляем подзадачи в третий эпик и меняем все статусы на DONE");
        subtaskID = taskManager.addSubtask(testSubtask7);
        taskManager.getSubtask(subtaskID).setStatus(Status.DONE);
        subtaskID = taskManager.addSubtask(testSubtask8);
        taskManager.getSubtask(subtaskID).setStatus(Status.DONE);
        subtaskID = taskManager.addSubtask(testSubtask9);
        taskManager.getSubtask(subtaskID).setStatus(Status.DONE);
        taskManager.updateSubtask(testSubtask7);

        System.out.println("\nПроверяем что получилось с эпиками и подзадачами\n");
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        //тесты не очень получились в следующем спринте будут юнит тесты

        System.out.println("\nУдаляем подзадачу и проверяем её и эпик\n");
        taskManager.removeSubtask(subtaskID);
        System.out.println(taskManager.getSubtask(subtaskID));
        System.out.println(taskManager.getEpic(epicID));
        System.out.println("\nПечатаем все задачи\n");
        System.out.println(taskManager.getAllTasks());
        System.out.println("\nПечатаем все подзадачи\n");
        System.out.println(taskManager.getAllSubtasks());
        System.out.println("\nПечатаем все эпики\n");
        System.out.println(taskManager.getAllEpics());
    }
}