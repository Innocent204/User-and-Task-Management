package com.example.User.and.Task.Management.System.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("")
    public ResponseEntity<?> createTask(@RequestBody Task task, Authentication authentication) {
        Task created = taskService.createTask(task, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("")
    public ResponseEntity<?> getUserTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String dueDate,
            @RequestParam(required = false) String createdAt,
            @RequestParam(required = false) String sortBy,
            Authentication authentication) {
        // Parse params as needed
        TaskStatus statusEnum = status != null ? TaskStatus.valueOf(status) : null;
        // TODO: parse dueDate/createdAt if needed
        var tasks = taskService.getUserTasks(authentication.getName(), statusEnum, null, null, sortBy);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id, Authentication authentication) {
        var taskOpt = taskService.getTaskById(id);
        if (taskOpt.isEmpty()) return ResponseEntity.notFound().build();
        Task task = taskOpt.get();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && !task.getUser().getUsername().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
        }
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task updatedTask, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        Task task = taskService.updateTask(id, updatedTask, authentication.getName(), isAdmin);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        taskService.deleteTask(id, authentication.getName(), isAdmin);
        return ResponseEntity.noContent().build();
    }
}

