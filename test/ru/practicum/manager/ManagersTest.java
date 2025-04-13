package ru.practicum.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManagersTest {
    @Test
    public void getDefaultTest() {
        assertNotNull(Managers.getDefault());
    }

    @Test
    public void getDefaultHistoryTest() {
        assertNotNull(Managers.getDefaultHistory());
    }
}
