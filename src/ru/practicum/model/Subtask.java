package ru.practicum.model;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
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
