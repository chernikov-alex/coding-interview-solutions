package com.alexchernikov.flow.reactivestreams;

import java.util.concurrent.Flow;

public class CustomSubscription implements Flow.Subscription {
    private long messagesCount;

    @Override
    public void request(long n) {
        this.messagesCount = n;
    }

    @Override
    public void cancel() {
        messagesCount = 0;
    }

    public long getMessagesCount() {
        return messagesCount;
    }
}