package Sc2001Lab2;

public class ArrayPriorityQueue {
    private int[] elements;
    private int[] priorities;
    private int size;

    public ArrayPriorityQueue(int capacity) {
        elements = new int[capacity];
        priorities = new int[capacity];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insertElement(int element, int priority) {
        if (size == elements.length) {
            resize(); // Resize if queue is full
        }

        elements[size] = element;
        priorities[size] = priority;
        size++;
    }

    // Method to resize the internal arrays
    private void resize() {
        int newCapacity = elements.length * 2;
        int[] newElements = new int[newCapacity];
        int[] newPriorities = new int[newCapacity];

        System.arraycopy(elements, 0, newElements, 0, elements.length);
        System.arraycopy(priorities, 0, newPriorities, 0, priorities.length);

        elements = newElements;
        priorities = newPriorities;
    }

    public int extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }

        int minIndex = 0;
        for (int i = 1; i < size; i++) {
            if (priorities[i] < priorities[minIndex]) {
                minIndex = i;
            }
        }

        int minElement = elements[minIndex];

        // Move the last element to the extracted position
        elements[minIndex] = elements[size - 1];
        priorities[minIndex] = priorities[size - 1];
        size--;

        return minElement;
    }

    public void updatePriority(int element, int newPriority) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == element) {
                priorities[i] = newPriority;
                return;
            }
        }

        throw new IllegalArgumentException("Element not found in priority queue");
    }
}


