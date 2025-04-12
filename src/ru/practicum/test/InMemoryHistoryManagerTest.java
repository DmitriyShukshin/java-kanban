import org.junit.jupiter.api.Test;
import ru.practicum.manager.HistoryManager;
import ru.practicum.manager.InMemoryHistoryManager;
import ru.practicum.model.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InMemoryHistoryManagerTest {

    @Test
    void inMemoryHistoryManagerTest() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Task1", "Description1");

        assertEquals(0, historyManager.getHistory().size());
        historyManager.add(task1);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void  historySizeAndRemovingFirstTest() {
        HistoryManager history = new InMemoryHistoryManager();
        ArrayList<Task> tasks = new ArrayList<>(11);
        for (int i = 0; i <= 10; i++) {
            tasks.add(new Task("Name " + i, "description " + i, i));
            history.add(tasks.get(i));
        }
        assertNotEquals(tasks.getFirst(), history.getHistory().getFirst());
        assertEquals(10, history.getHistory().size());
    }
}