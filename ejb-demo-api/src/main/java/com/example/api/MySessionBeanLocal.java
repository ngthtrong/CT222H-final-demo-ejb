package com.example.api;

import jakarta.ejb.Local;
import java.util.List;

@Local
public interface MySessionBeanLocal {
    void addUser(String name);
    List<String> getAllUserNames();
}
