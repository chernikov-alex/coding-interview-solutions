package com.alexchernikov.designpatterns.strategy.spring;

import com.alexchernikov.datastructures.queue.ArrayQueue;
import com.alexchernikov.datastructures.queue.Queue;
import com.alexchernikov.designpatterns.strategy.core.Storage;
import com.alexchernikov.designpatterns.strategy.core.StorageStrategy;
import com.alexchernikov.designpatterns.strategy.core.StorageType;
import org.springframework.stereotype.Service;

@Service
@StorageStrategy(type = StorageType.FIFO)
public class FifoStorage<T> implements Storage<T> {
    private final Queue<T> queue = new ArrayQueue<>();

    @Override
    public void add(T item) {
        queue.enqueue(item);
    }

    @Override
    public T remove() {
        return queue.dequeue();
    }

    @Override
    public T peek() {
        return queue.peek();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
