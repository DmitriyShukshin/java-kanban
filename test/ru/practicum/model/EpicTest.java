package ru.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {
    @Test
    void shouldEqualsReturnTruEpicEqualsId() {
        Epic epic1 = new Epic("Epic1", "Epic1 description", 5);
        Epic epic2 = new Epic("Epic2", "Epic2 description", 5);

        assertEquals(epic1, epic2);
    }
}
