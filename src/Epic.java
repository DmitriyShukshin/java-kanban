import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksList;

    public Epic(String name, String description) {
        super(name, description);
        subtasksList = new ArrayList<>();
    }

    public List<Integer> getSubtasksList() {
        return subtasksList;
    }
}