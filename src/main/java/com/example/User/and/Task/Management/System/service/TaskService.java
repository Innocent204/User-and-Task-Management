package com.example.User.and.Task.Management.System.service;

import com.example.User.and.Task.Management.System.model.Task;
import com.example.User.and.Task.Management.System.model.TaskStatus;
import com.example.User.and.Task.Management.System.model.User;
import com.example.User.and.Task.Management.System.repository.TaskRepository;
import com.example.User.and.Task.Management.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public Task createTask(Task task, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public List<Task> getUserTasks(String username, TaskStatus status, LocalDateTime dueDate, LocalDateTime createdAt, String sortBy) {
        // Filtering and sorting 
        User user = userRepository.findByUsername(username).orElseThrow();
        if (status != null) {
            return taskRepository.findByUserAndStatus(user, status, Sort.by(Sort.Direction.ASC, sortBy != null ? sortBy : "createdAt"));
        }
        return taskRepository.findByUser(user, Sort.by(Sort.Direction.ASC, sortBy != null ? sortBy : "createdAt"));
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, Task updatedTask, String username, boolean isAdmin) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (!isAdmin && !task.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You do not have permission to update this task");
        }
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setDueDate(updatedTask.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id, String username, boolean isAdmin) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (!isAdmin && !task.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You do not have permission to delete this task");
        }
        taskRepository.delete(task);
    }

    // Admin methods
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public List<Task> getTasksByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return taskRepository.findByUser(user, Sort.by(Sort.Direction.ASC, "createdAt"));
    }
    public void deleteTaskAsAdmin(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
