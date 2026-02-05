package com.alexchernikov.flow.asyncprocessing;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class MessageGenerator {

    private final EventQueue queue;
    private final Random random = new Random();

    public MessageGenerator(EventQueue queue) {
        this.queue = queue;
    }

    public void generate(int count) {
        for (int i=0; i < count ; i++) {
            TextEvent event = new TextEvent(
                    UUID.randomUUID(),
                    Instant.now().minusSeconds(random.nextInt(10)),
                    "message-" + i
            );
            queue.publish(event);
        }
    }
}
