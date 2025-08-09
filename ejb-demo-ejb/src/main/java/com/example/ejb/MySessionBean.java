package com.example.ejb;

import com.example.api.MySessionBeanLocal;
import jakarta.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Stateless(name = "MySessionBean")
public class MySessionBean implements MySessionBeanLocal {

    // Thread-safe list to store messages in memory
    private static final List<String> messages = new CopyOnWriteArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void processMessage(String message) {
        // Process the message received from MDB
        String timestamp = LocalDateTime.now().format(formatter);
        String processedMessage = String.format("[%s] Processed: %s", timestamp, message);
        
        // Add to message list
        addMessage(processedMessage);
        
        // Log the processing
        System.out.println("SessionBean processed message: " + processedMessage);
        
        // Here you can add additional business logic for message processing
        performMessageBusinessLogic(message);
    }

    @Override
    public void addMessage(String message) {
        messages.add(message);
        // Keep only last 100 messages to prevent memory issues
        if (messages.size() > 100) {
            messages.remove(0);
        }
    }

    @Override
    public List<String> getAllMessages() {
        return new ArrayList<>(messages);
    }

    @Override
    public int getMessageCount() {
        return messages.size();
    }

    @Override
    public String getLatestMessage() {
        if (messages.isEmpty()) {
            return "No messages received yet";
        }
        return messages.get(messages.size() - 1);
    }

    private void performMessageBusinessLogic(String message) {
        // Custom business logic for message processing
        // Example: validate, transform, or trigger other operations
        
        if (message.toLowerCase().contains("urgent")) {
            System.out.println("URGENT message detected: " + message);
            // Could trigger alerts or special handling
        }
        
        if (message.toLowerCase().contains("error")) {
            System.out.println("ERROR message detected: " + message);
            // Could log errors or trigger error handling
        }
        
        // Add more business logic as needed
    }
}
