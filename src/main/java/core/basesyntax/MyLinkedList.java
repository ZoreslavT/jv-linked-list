package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private Node<T> currentNode;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        currentNode = new Node<>(value);
        Node<T> t = tail;
        if (head == null) {
            head = new Node<>(value);
            head.next = tail;
        } else if (tail == null) {
            tail = new Node<>(value);
            head.next = tail;
            tail.prev = head;
        } else {
            tail = currentNode;
            tail.prev = t;
            tail.prev.prev.next = t;
            t.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        currentNode = new Node<>(value);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> next = getNode(index);
        Node<T> prev = next.prev;
        currentNode.prev = prev;
        currentNode.next = next;
        if (prev == null) {
            head = currentNode;
        } else {
            prev.next = currentNode;
            next.prev = currentNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        if (index < (size / 2)) {
            Node<T> result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
            return result.value;
        } else if (index == size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        } else {
            Node<T> result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
            return result.value;
        }
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size && head != null) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        Node<T> removedNode = getNode(index);
        T removedValue = removedNode.value;
        removedNode.value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size && head != null) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        return unLink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> n = head; n != null; n = n.next) {
                if (n.value == null) {
                    unLink(n);
                    return true;
                }
            }
        } else {
            for (Node<T> n = head; n != null; n = n.next) {
                if (n.value == object || n.value != null && n.value.equals(object)) {
                    unLink(n);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private T unLink(Node<T> currentNode) {
        Node<T> next = currentNode.next;
        Node<T> prev = currentNode.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            currentNode.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            currentNode.next = null;
        }
        size--;
        return currentNode.value;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (size == 1) {
            return head;
        }
        Node<T> result;
        if (index < (size / 2)) {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
                return result;
            }
        } else {
            result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }
}
