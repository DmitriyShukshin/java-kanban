package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Status;
import ru.practicum.model.Task;

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
    void likedListRemoveLastNodeTest() {
        Task task1 = new Task("Задача 1", "Тестовая задача 1", Status.NEW);
        manager.add(task1);
        manager.remove(task1.getId());
        assertNotEquals(manager.getHistory().getFirst(), null,
                "Не удаляется единственный элемент.");
    }

    @Test
    void linkedListRemoveHeadTest() {
        Task task1 = new Task("Задача 1", "Тестовая задача 1", 1, Status.NEW);
        Task task2 = new Task("Задача 2", "Тестовая задача 2", 2, Status.NEW);
        Task task3 = new Task("Задача 3", "Тестовая задача 3", 3, Status.NEW);
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        assertEquals(3, manager.getHistory().size());
        manager.remove(task1.getId());
        assertEquals(2, manager.getHistory().size());
        assertEquals(task2, manager.getHistory().getFirst());
        assertEquals(task3, manager.getHistory().getLast());
    }

    @Test
    void linkedListRemoveTeilTest() {
        Task task1 = new Task("Задача 1", "Тестовая задача 1", 1, Status.NEW);
        Task task2 = new Task("Задача 2", "Тестовая задача 2", 2, Status.NEW);
        Task task3 = new Task("Задача 3", "Тестовая задача 3", 3, Status.NEW);
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        assertEquals(3, manager.getHistory().size());
        manager.remove(task3.getId());
        assertEquals(2, manager.getHistory().size());
        assertEquals(task1, manager.getHistory().getFirst());
        assertEquals(task2, manager.getHistory().getLast());
    }

    @Test
    void linkedListRemoveMiddleTest() {
        Task task1 = new Task("Задача 1", "Тестовая задача 1", 1, Status.NEW);
        Task task2 = new Task("Задача 2", "Тестовая задача 2", 2, Status.NEW);
        Task task3 = new Task("Задача 3", "Тестовая задача 3", 3, Status.NEW);
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        assertEquals(3, manager.getHistory().size());
        manager.remove(task2.getId());
        assertEquals(2, manager.getHistory().size());
        assertEquals(task1, manager.getHistory().getFirst());
        assertEquals(task3, manager.getHistory().getLast());
    }
}