package com.example.User.and.Task.Management.System.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final TaskService taskService;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<?> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByUserId(userId));
    }

    @DeleteMapping("/users/{userId}/tasks/{taskId}")
    public ResponseEntity<?> deleteUserTask(@PathVariable Long userId, @PathVariable Long taskId) {
        // Optionally check if task belongs to userId
        taskService.deleteTaskAsAdmin(taskId);
        return ResponseEntity.noContent().build();
    }
}
