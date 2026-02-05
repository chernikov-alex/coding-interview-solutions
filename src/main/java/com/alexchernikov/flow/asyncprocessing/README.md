## Problem description : 
Design and simulate simple asynchronous processing system using Java.

### Solution : 
- I assume at-least-once delivery, so the handler is idempotent.
- Events may arrive out of order, so I compare timestamps to process only the latest event.
- Failures are isolated per event - no blocking the stream.
- The producer doesn't know who consumes the events.
- This structure would work the same with Kafka/JMS.

