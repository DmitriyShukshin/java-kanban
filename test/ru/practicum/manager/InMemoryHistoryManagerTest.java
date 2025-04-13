package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Status;
import ru.practicum.model.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InMemoryHistoryManagerTest {
    HistoryManager manager = Managers.getDefaultHistory();

    @BeforeEach
    void beforeEach() {
        manager = Managers.getDefaultHistory();
    }

    @Test
    void inMemoryHistoryManagerTest() {
        Task task1 = new Task("Task1", "Description1");

        assertEquals(0, manager.getHistory().size());
        manager.add(task1);
        assertEquals(1, manager.getHistory().size());
    }

    @Test
    void  historySizeAndRemovingFirstTest() {
        ArrayList<Task> tasks = new ArrayList<>(11);
        for (int i = 0; i <= 10; i++) {
            tasks.add(new Task("Name " + i, "description " + i, i));
            manager.add(tasks.get(i));
        }
        assertNotEquals(tasks.getFirst(), manager.getHistory().getFirst());
        assertEquals(10, manager.getHistory().size());
    }

    @Test
    void oldVersionOfTaskFromHistoryTest() {
        Task task1 = new Task("Задача 1", "Тестовая задача 1", Status.NEW);
        manager.add(task1);
        task1.setStatus(Status.IN_PROGRESS);
        manager.add(task1);
        assertNotEquals(manager.getHistory().getFirst().getStatus(), manager.getHistory().getLast().getStatus(),
                "Не сохранено состояние на момент получения задачи");
    }
}