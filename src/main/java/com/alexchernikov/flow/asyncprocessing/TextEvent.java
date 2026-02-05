package com.alexchernikov.flow.asyncprocessing;

import java.time.Instant;
import java.util.UUID;

public record TextEvent(
        UUID eventId,
        Instant timestamp,
        String payload
) {
}
