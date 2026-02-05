package com.alexchernikov.flow.asyncprocessing;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.security.MessageDigest;

public class AsyncEventProcessor {

    private final EventQueue queue;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    private final Set<String> processedEvents = ConcurrentHashMap.newKeySet();

    // last processed timestamp (ordering awareness)
    private volatile Instant lastProcessedTimestamp = Instant.MIN;

    public AsyncEventProcessor(EventQueue queue) {
        this.queue = queue;
    }

    public void start() {
        CompletableFuture.runAsync(this::pollLoop, executor);
    }

    private void pollLoop() {
        while (true) {
            try {
                TextEvent event = queue.take();
                processAsync(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void processAsync(TextEvent event) {
        CompletableFuture
                .runAsync(() -> handleEvent(event), executor)
                .exceptionally(ex -> {
                    System.err.println("Failed to process event " + event.eventId() + " : " + ex.getMessage());
                    return null; // would go to DLQ in real system
                });
    }

    private void handleEvent(TextEvent event) {
        // Idempotency
        if (!processedEvents.add(event.eventId().toString())) {
            return; // duplicate, already processed
        }

        // Ordering awareness
        if (event.timestamp().isBefore(lastProcessedTimestamp)) {
            System.out.println("Stale event ignored: " + event.eventId());
            return;
        }

        // Simulated business logic
        String hash = sha256(event.payload());

        lastProcessedTimestamp = event.timestamp();

        System.out.printf(
                "Processed event %s | ts=%s | hash=%s%n",
                event.eventId(),
                event.timestamp(),
                hash
        );
    }

    private String sha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
