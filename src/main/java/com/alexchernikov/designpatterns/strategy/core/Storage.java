package com.alexchernikov.designpatterns.strategy.core;

public interface Storage <T> {
    void add(T item);
    T remove();
    T peek();
    int size();
    boolean isEmpty();
}
