package com.example.api;

import jakarta.ejb.Local;
import java.util.List;

@Local
public interface MySessionBeanLocal {
    // Message processing methods
    void processMessage(String message);
    void addMessage(String message);
    List<String> getAllMessages();
    int getMessageCount();
    String getLatestMessage();
}
