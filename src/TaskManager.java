import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    static int idCounter = 1;
    private Map<Integer, Task> taskMap;
    private Map<Integer, Subtask> subtaskMap;
    private Map<Integer, Epic> epicMap;

    TaskManager() {
        taskMap = new HashMap<>();
        subtaskMap = new HashMap<>();
        epicMap = new HashMap<>();
    }

    public Map<Integer, Task> getAllTasks() {
        if (taskMap.isEmpty()) {
            System.out.println("Здесь пока нет задач.");
            return null;
        }
        return taskMap;
    }

    public Map<Integer, Subtask> getAllSubtasks() {
        if (subtaskMap.isEmpty()) {
            System.out.println("Здесь пока нет подзадач.");
            return null;
        }
        return subtaskMap;
    }

    public Map<Integer, Epic> getAllEpics() {
        if (epicMap.isEmpty()) {
            System.out.println("Здесь пока нет эпиков.");
        }
        return epicMap;
    }

    public void cleanAllTasks() {
        taskMap.clear();
    }

    public void cleanAllSubtasks() {
        for (int subtaskId : subtaskMap.keySet()) {
            removeSubtask(subtaskId);
        }
        subtaskMap.clear();
    }

    public void cleanAllEpics() {
        epicMap.clear();
    }

    public Task getTask(int id) {
        if (!taskMap.containsKey(id)) {
            System.out.println("Задача с id - " + id + " не найдена.");
        }
        return taskMap.get(id);
    }

    public Subtask getSubtasks(int id) {
        if (!subtaskMap.containsKey(id)) {
            System.out.println("Подзадача с id - " + id + " не найдена.");
        }
        return subtaskMap.get(id);
    }

    public Epic getEpic(int id) {
        if (!epicMap.containsKey(id)) {
            System.out.println("Подзадача с id - " + id + " не найдена.");
        }
        return epicMap.get(id);
    }

    public void addTask(Task task) {
        if (task != null) {
            taskMap.put(task.getId(), task);
        } else {
            System.out.println("Не пытайтесь добавить пустую ссылку");
        }
    }

    public void addSubtask(Subtask subtask) {
        if (subtask != null) {
            subtaskMap.put(subtask.getId(), subtask);
            epicMap.get(subtask.getEpicId()).getSubtasksList().add(subtask.getId());
        } else {
            System.out.println("Не пытайтесь добавить пустую ссылку");
        }
    }

    public void addEpic(Epic epic) {
        if (epic != null) {
            epicMap.put(epic.getId(), epic);
        } else {
            System.out.println("Не пытайтесь добавить пустую ссылку");
        }
    }

    public void updateTask(Task task) {
        if (taskMap.containsKey(task.getId())) {
            taskMap.put(task.getId(), task);
            System.out.println("Задача с id - " + task.getId() + " обновлена.");
        } else {
            System.out.println("Задача с id - " + task.getId() + " не найдена.");
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtaskMap.containsKey(subtask.getId())) {
            subtaskMap.put(subtask.getId(), subtask);
            checkEpicStatus(subtask.getEpicId());
            System.out.println("Подзадача с id - " + subtask.getId() + " обновлена.");
        } else {
            System.out.println("Задача с id - " + subtask.getId() + " не найдена.");
        }
    }

    public void updateEpic(Epic epic) {
        if (epicMap.containsKey(epic.getId())) {
            epicMap.put(epic.getId(), epic);
            checkEpicStatus(epic.getId());
            System.out.println("Эпик с id - " + epic.getId() + " обновлена.");
        }
        System.out.println("Эпик с id - " + epic.getId() + " не найдена.");
    }

    public void removeTask(int id) {
        if (taskMap.containsKey(id)) {
            System.out.println("Задача \"" + taskMap.get(id).getName() + "\" удалена.");
            taskMap.remove(id);
        } else {
            System.out.println("Задача с id - " + id + " не найдена.");
        }
    }

    public void removeSubtask(int id) {
        if (subtaskMap.containsKey(id)) {
            Subtask tempSubtask = subtaskMap.get(id);
            System.out.println("Задача \"" + subtaskMap.get(id).getName() + "\" удалена.");
            subtaskMap.remove(id);
            epicMap.get(tempSubtask.getEpicId()).getSubtasksList().remove(Integer.valueOf(id));
            checkEpicStatus(tempSubtask.getEpicId());
        } else {
            System.out.println("Задача с id - " + id + " не найдена.");
        }
    }

    public void removeEpic(int id) {
        for (Subtask subtask : subtaskMap.values()){
            if (subtask.getEpicId() == id) {
                subtask.setEpicId(0);
            }
        }
        epicMap.remove(id);
    }

    public Map<Integer, Subtask> getEpicSubtasks(Epic epic) {
        return subtaskMap; // исправить
    }

    private void checkEpicStatus(int id) {
        if (epicMap.containsKey(id)) {
            Status newEpicStatus = null;
            if (epicMap.get(id).getSubtasksList().isEmpty())
                newEpicStatus = Status.NEW;
            else {
                for (int subtaskId : epicMap.get(id).getSubtasksList()) {
                    if (subtaskMap.get(subtaskId).getStatus() != Status.NEW) {
                        newEpicStatus = Status.IN_PROGRESS;
                        break;
                    }
                    newEpicStatus = Status.NEW;
                    epicMap.get(id).setStatus(newEpicStatus);
                    return;
                }
                for (int subtaskId : epicMap.get(id).getSubtasksList()) {
                    if (subtaskMap.get(subtaskId).getStatus() == Status.IN_PROGRESS
                            || subtaskMap.get(subtaskId).getStatus() == Status.NEW) {
                        newEpicStatus = Status.IN_PROGRESS;
                        break;
                    }
                    newEpicStatus = Status.DONE;
                }
            }
            epicMap.get(id).setStatus(newEpicStatus);
        } else {
            System.out.println("Эпик с id - " + id + " не найден.");
        }
    }
}