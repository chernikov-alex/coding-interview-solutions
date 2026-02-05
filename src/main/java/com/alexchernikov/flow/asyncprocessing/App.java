package com.alexchernikov.flow.asyncprocessing;

public class App {
    public static void main(String[] args) throws InterruptedException {
        EventQueue queue = new EventQueue();
        AsyncEventProcessor processor = new AsyncEventProcessor(queue);
        MessageGenerator generator = new MessageGenerator(queue);

        processor.start();
        generator.generate(20);

        Thread.sleep(2000);
        System.exit(0);
    }
}
