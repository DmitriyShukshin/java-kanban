package ru.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    void shouldEqualsReturnTruTasksEqualsId() {
        Task task1 = new Task("Task1", "Task1 description", 5, Status.DONE);
        Task task2 = new Task("Task2", "Task2 description", 5, Status.NEW);

        assertEquals(task1, task2);
    }
}
