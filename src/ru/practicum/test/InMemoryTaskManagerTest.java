import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.practicum.manager.Managers;
import ru.practicum.manager.TaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

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
    void oldVersionOfTaskFromHistoryTest() {
        int taskId;

        Task task1 = new Task("Задача 1", "Тестовая задача 1");
        taskId = manager.addNewTask(task1);
        manager.getTask(taskId);
        manager.updateTask(new Task(task1.getName(), task1.getDescription(), task1.getId(), Status.IN_PROGRESS));
        manager.getTask(taskId);
        assertNotEquals(manager.getHistory().getFirst().getStatus(), manager.getHistory().getLast().getStatus(),
                "Не сохранено состояние на момент получения задачи");
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