package com.example.api;

import jakarta.ejb.Remote;

@Remote
public interface MySessionBeanRemote {
    String getGreeting(String name);
}
