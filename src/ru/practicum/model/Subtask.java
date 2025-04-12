package ru.practicum.model;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId, int id) {
        super(name, description, id);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId, Status status){
        super(name, description, status);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId, int id, Status status){
        super(name, description, id, status);
        this.epicId = epicId;
    }

    public Subtask(Subtask subtask) {
        super(subtask);
        this.epicId = subtask.getEpicId();
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return this.getClass() + "{\n" +
                "   ID = " + id + '\n' +
                "   epicID = " + epicId + '\n' +
                "   name = " + name + '\n' +
                "   description = " + description + '\n' +
                "   status = " + status + '\n' +
                "}\n";
    }
}
