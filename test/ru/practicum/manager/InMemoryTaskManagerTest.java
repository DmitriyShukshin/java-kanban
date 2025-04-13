package ru.practicum.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager manager = Managers.getDefault();

    @AfterEach
    void afterEach() {
        manager.cleanAllEpics();
        manager.cleanAllTasks();
    }

    @Test
    void initializationTest() {
        assertNotNull(manager.getTasks());
        assertNotNull(manager.getAllSubtasks());
        assertNotNull(manager.getAllEpics());
        assertNotNull(manager.getHistory());
    }

    @Test
    void managerTest() {
        int taskID;
        int epicID;
        int subtaskID;
        Task testTask1 = new Task("Задача 1", "Тестовая задача 1");
        taskID = manager.addNewTask(testTask1);
        assertEquals(testTask1, manager.getTask(taskID), "Задача не добавлена");
        Epic testEpic1 = new Epic("Эпик 1", "Тестовый эпик 1");
        epicID = manager.addEpic(testEpic1);
        assertEquals(testEpic1, manager.getEpic(epicID), "Эпик не добавлен");
        Subtask testSubtask1 = new Subtask("Подзадача 1", "Тестовая подзадача 1", epicID);
        subtaskID = manager.addNewSubtask(testSubtask1);
        assertEquals(testSubtask1, manager.getSubtask(subtaskID), "Подзадача не добавлена");
        assertEquals(3, manager.getHistory().size(), "История за записывается");
    }

    @Test
    void addHistoryAfterGettingTest() {
        int taskId;
        int epicId;
        int subtaskId;

        Task task = new Task("Задача", "Тестовая задача");
        taskId = manager.addNewTask(task);
        Epic epic = new Epic("Эпик", "Тестовый эпик");
        epicId = manager.addEpic(epic);
        Subtask subtask = new Subtask("Подзадача", "Тестовая подзадача", epicId);
        subtaskId = manager.addNewSubtask(subtask);

        manager.getTask(taskId);
        assertEquals(1, manager.getHistory().size(),
                "При получении, задача не была добавлена в историю.");
        manager.getEpic(epicId);
        assertEquals(2, manager.getHistory().size(),
                "При получении, эпик не был добавлен в историю.");
        manager.getSubtask(subtaskId);
        assertEquals(3, manager.getHistory().size(),
                "При получении, подзадача не была добавлена в историю");
    }

    @Test
    void epicCheckStatusTest() {
        Epic epic = new Epic("Epic", "Description");
        int epicId = manager.addEpic(epic);
        Subtask subtask1 = new Subtask("Name1", "Description1", epicId);
        Subtask subtask2 = new Subtask("Name2", "Description2", epicId, Status.IN_PROGRESS);
        Subtask subtask3 = new Subtask("Name3", "Description3", epicId, Status.DONE);

        assertEquals(Status.NEW, epic.getStatus(), "Статус пустого эпика должен быть NEW");
        manager.addNewSubtask(subtask1);
        assertEquals(Status.NEW, epic.getStatus(),
                "Статус эпика должен быть NEW когда у всех подзадач статус NEW");
        int subtask2Id = manager.addNewSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(),
                "Статус эпика должен быть IN_PROGRESS когда есть подзадача с таким статусом");
        manager.addNewSubtask(subtask3);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(),
                "Статус эпика должен быть IN_PROGRESS когда есть подзадача с таким статусом");
        manager.removeSubtask(subtask2Id);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(),
                "Статус эпика должен быть IN_PROGRESS когда есть подзадачи со статусами NEW и DONE");
        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);
        assertEquals(Status.DONE, epic.getStatus(),
                "Статус эпика должен быть DONE когда статус всех подзадач DONE");
    }
}