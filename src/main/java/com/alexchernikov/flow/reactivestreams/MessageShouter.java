package com.alexchernikov.flow.reactivestreams;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MessageShouter implements Publisher<ImportantMessage> {

    @Override
    public void subscribe(Subscriber<? super ImportantMessage> subscriber) {
        // subscriber.onSubscribe(...);
        // ToDo
    }

    public void shout(ImportantMessage message) {
        System.out.println("Shouting: " + message);
        // subscriber.onNext(message)
        //Todo
    }

    public void close() {
        System.out.println("Shouter complete");
        // ToDo
    }

}