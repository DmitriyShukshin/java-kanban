package ru.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubtaskTest {
    @Test
    void shouldEqualsReturnTruSubtaskEqualsId() {
        Subtask subTask1 = new Subtask("Subtask1", "Subtask1 description", 1, 5);
        Subtask subTask2 = new Subtask("Subtask2", "Subtask2 description", 2, 5);

        assertEquals(subTask1, subTask2);
    }
}
