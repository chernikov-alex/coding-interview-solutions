package com.alexchernikov.datastructures.stack;

public class ArrayStack<T> implements Stack<T> {
    private static final int INITIAL_CAPACITY = 10;
    private T[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        elements = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(int initialCapacity) {
        elements = (T[]) new Object[initialCapacity];
        size = 0;
    }

    @Override
    public void push(T item) {
        ensureCapacity();
        elements[size++] = item;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T item = elements[--size];
        elements[size] = null; // Avoid memory leak
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return elements[size - 1];
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
            System.arraycopy(elements, 0, newArray, 0, elements.length);
            elements = newArray;
        }
    }
}
