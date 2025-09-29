package com.example.User.and.Task.Management.System.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
            "message", "Welcome to User and Task Management System API",
            "version", "1.0.0",
            "endpoints", "/api/auth/register, /api/auth/login, /api/tasks, /api/admin"
        );
    }
}