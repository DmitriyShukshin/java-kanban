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
            if (history.containsKey(task.getId())) {
                history.remove(task.getId());
            }
            linkLast(new Task(task.getName(), task.getDescription(), task.getId(), task.getStatus()));
        }
    }

    @Override
    public void remove(int id) {
        removeNode(history.get(id));
    }

    private void linkLast(Task task) {
        if (head == null) {
            Node<Task> newNode = new Node<>(task, null, null);
            head = newNode;
            tail = newNode;
        } else if (head == tail) {
            tail = new Node<>(task, null, head);
            head.setNext(tail);
        } else {
            tail = new Node<>(task, null, tail);
            tail.getPrev().setNext(tail);
        }
        history.put(task.getId(), tail);
    }

    private List<Task> getTasks() {
        List<Task> finalHistory = new ArrayList<>();
        Node<Task> node = head;
        while(node != null) {
            finalHistory.add(node.task);
            node = node.next;
        }
        return finalHistory;
    }

    private void removeNode(Node<Task> node) {
        if (node != null) {
            Node<Task> next = node.getNext();
            Node<Task> prev = node.getPrev();
            if (head == tail) {
                head = null;
                tail = null;
            } else if (node == head) {
                next.setPrev(null);
                head = next;
            } else if (node == tail) {
                prev.setNext(null);
                tail = node;
            } else {
                prev.setNext(next);
                next.setPrev(prev);
            }
        }
    }
}
