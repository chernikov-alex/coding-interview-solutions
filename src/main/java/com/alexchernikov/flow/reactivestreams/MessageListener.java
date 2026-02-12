package com.alexchernikov.flow.reactivestreams;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MessageListener implements Subscriber<ImportantMessage> {

    private final int id;

    public MessageListener(int id) {
        this.id = id;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("Listener " + id + " successfully subscribed");
        // when the Listener requests n messages, it will receive up to n additional onNext invocations
        subscription.request(1);
        // Todo
    }

    @Override
    public void onNext(ImportantMessage item) {
        System.out.println("Listener " + id + ": " + item);
        // Todo
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Listener " + id + " Error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Listener " + id + " Completed");
    }

    public void mute() {
        // Todo
    }

    public void unmute() {
        // Todo
    }
}
