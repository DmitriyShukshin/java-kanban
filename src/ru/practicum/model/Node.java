package ru.practicum.model;

public class Node<T> {

    public Task task;
    public Node<Task> next;
    public Node<Task> prev;

    public Node(Task task) {
        this.task = task;
        this.next = null;
        this.prev = null;
    }


    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Node<Task> getNext() {
        return next;
    }

    public void setNext(Node<Task> next) {
        this.next = next;
    }

    public Node<Task> getPrev() {
        return prev;
    }

    public void setPrev(Node<Task> prev) {
        this.prev = prev;
    }
}
