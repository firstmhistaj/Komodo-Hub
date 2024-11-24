package com.example.komodo_hub;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    private String message;
    private String sender; // e.g., "Teacher" or "Student"
    private String timestamp;

    public ChatMessage(String message, String sender) {
        this.message = message;
        this.sender = sender;
        this.timestamp = new SimpleDateFormat("hh:mm a").format(new Date()); // Store timestamp
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return sender + " (" + timestamp + "): " + message;
    }
}
