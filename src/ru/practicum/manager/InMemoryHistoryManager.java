package ru.practicum.manager;

import ru.practicum.model.Node;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private HashMap<Integer, Node<Task>> history;
    private Node<Task> head;
    private Node<Task> tail;

    public InMemoryHistoryManager() {
        this.history = new HashMap<>();
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            history.remove(task.getId());
            linkLast(new Node<>(new Task(task.getName(), task.getDescription(), task.getId(), task.getStatus())));
        }
    }

    @Override
    public void remove(int id) {
        removeNode(history.get(id));
    }

    private void linkLast(Node<Task> node) {
        if (tail == null) {
            head = node;
        } else {
            tail.setNext(node);
            node.setPrev(tail);
        }
        tail = node;
        history.put(node.task.getId(), tail);
    }

    private List<Task> getTasks() {
        List<Task> finalHistory = new ArrayList<>();
        Node<Task> node = head;
        while (node != null) {
            finalHistory.add(node.task);
            node = node.next;
        }
        return finalHistory;
    }

    private void removeNode(Node<Task> node) {
        if (node != null) {
            Node<Task> next = node.getNext();
            Node<Task> prev = node.getPrev();
            if (next == null && prev == null) {
                head = null;
                tail = null;
            } else if (prev == null) {
                next.setPrev(null);
                head = next;
            } else if (next == null) {
                prev.setNext(null);
                tail = prev;
            } else {
                prev.setNext(next);
                next.setPrev(prev);
            }
        }
    }
}
