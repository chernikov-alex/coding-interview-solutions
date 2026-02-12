package com.alexchernikov.flow.reactivestreams;

public class ImportantMessage {
    private final long id;
    private final String message;

    public ImportantMessage(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ImportantMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}