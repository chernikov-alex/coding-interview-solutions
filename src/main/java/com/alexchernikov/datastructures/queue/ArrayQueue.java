package com.alexchernikov.datastructures.queue;

public class ArrayQueue<T> implements Queue<T> {
    private static final int INITIAL_CAPACITY = 10;
    private T[] elements;
    private int front;
    private int rear;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        elements = (T[]) new Object[INITIAL_CAPACITY];
        front = 0;
        rear = 0;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public ArrayQueue(int initialCapacity) {
        elements = (T[]) new Object[initialCapacity];
        front = 0;
        rear = 0;
        size = 0;
    }

    @Override
    public void enqueue(T item) {
        ensureCapacity();
        elements[rear] = item;
        rear = (rear + 1) % elements.length;
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T item = elements[front];
        elements[front] = null; // Avoid memory leak
        front = (front + 1) % elements.length;
        size--;
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return elements[front];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == elements.length) {
            T[] newArray = (T[]) new Object[elements.length * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = elements[(front + i) % elements.length];
            }
            elements = newArray;
            front = 0;
            rear = size;
        }
    }
}
