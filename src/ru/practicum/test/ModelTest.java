import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTest {
    @Test
    void shouldEqualsReturnTruTasksEqualsId() {
        Task task1 = new Task("Task1", "Task1 description", 5, Status.DONE);
        Task task2 = new Task("Task2", "Task2 description", 5, Status.NEW);

        assertEquals(task1, task2);
    }
    @Test
    void shouldEqualsReturnTruSubtaskEqualsId() {
        Subtask subTask1 = new Subtask("Subtask1", "Subtask1 description", 1, 5);
        Subtask subTask2 = new Subtask("Subtask2", "Subtask2 description", 2, 5);

        assertEquals(subTask1, subTask2);
    }
    @Test
    void shouldEqualsReturnTruEpicEqualsId() {
        Epic epic1 = new Epic("Epic1", "Epic1 description", 5);
        Epic epic2 = new Epic("Epic2", "Epic2 description", 5);

        assertEquals(epic1, epic2);
    }
}
