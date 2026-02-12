package com.alexchernikov.flow.reactivestreams;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class MessageShouter implements Publisher<ImportantMessage> {

    private final Map<Subscriber<? super ImportantMessage>, CustomSubscription> subscribersMap = new HashMap<>();

    @Override
    public void subscribe(Subscriber<? super ImportantMessage> subscriber) {
        CustomSubscription subscription = new CustomSubscription();
        subscribersMap.put(subscriber, subscription);
        subscriber.onSubscribe(subscription);

        // ToDo
    }

    public void shout(ImportantMessage message) {
        System.out.println("Shouting: " + message);
        //  Iterate over subscribers and filter active ones and call for each of them the onNext(message)
        for (Subscriber<? super ImportantMessage> subscriber: subscribersMap.keySet()) {
            CustomSubscription subscription = subscribersMap.get(subscriber);
            if(subscription.getMessagesCount()>0) {
                subscriber.onNext(message);
            }
        }
    }

    public void close() {
        System.out.println("Shouter complete");
    }

}
