package ru.practicum.model;

import ru.practicum.manager.TaskManager;

public class Task {
    protected int id;

    protected String name;
    protected String description;
    protected Status status;
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.getClass() + "{\n" +
                "   ID = " + id + '\n' +
                "   name = " + name + '\n' +
                "   description = " + description + '\n' +
                "   status = " + status + '\n' +
                "}\n";
    }
}
