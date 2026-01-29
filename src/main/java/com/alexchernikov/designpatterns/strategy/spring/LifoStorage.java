package com.alexchernikov.designpatterns.strategy.spring;

import com.alexchernikov.datastructures.stack.ArrayStack;
import com.alexchernikov.datastructures.stack.Stack;
import com.alexchernikov.designpatterns.strategy.core.Storage;

import com.alexchernikov.designpatterns.strategy.core.StorageStrategy;
import com.alexchernikov.designpatterns.strategy.core.StorageType;
import org.springframework.stereotype.Service;

@Service
@StorageStrategy(type = StorageType.LIFO)
public class LifoStorage <T> implements Storage<T> {
    private final Stack<T> stack = new ArrayStack<>();

    @Override
    public void add(T item) {
        stack.push(item);
    }

    @Override
    public T remove() {
        return stack.pop();
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
