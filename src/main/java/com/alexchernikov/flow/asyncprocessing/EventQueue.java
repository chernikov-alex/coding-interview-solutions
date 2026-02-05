package com.alexchernikov.flow.asyncprocessing;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EventQueue {
    private final BlockingQueue<TextEvent> queue = new LinkedBlockingQueue<>();

    public void publish(TextEvent event) {
        queue.offer(event);
    }

    public TextEvent take() throws InterruptedException {
        return queue.take();
    }
}
