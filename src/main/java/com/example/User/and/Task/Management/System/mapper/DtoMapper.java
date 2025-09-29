package com.example.User.and.Task.Management.System.mapper;

import com.example.User.and.Task.Management.System.dto.request.TaskRequest;
import com.example.User.and.Task.Management.System.dto.response.TaskResponse;
import com.example.User.and.Task.Management.System.dto.response.UserResponse;
import com.example.User.and.Task.Management.System.model.Task;
import com.example.User.and.Task.Management.System.model.User;
import com.example.User.and.Task.Management.System.model.Role;
import java.util.stream.Collectors;

public class DtoMapper {
    public static TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.getDueDate(),
            task.getCreatedAt()
        );
    }

    public static UserResponse toUserResponse(User user) {
        UserResponse resp = new UserResponse();
        resp.setId(user.getId());
        resp.setUsername(user.getUsername());
        resp.setRoles(user.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.toSet()));
        return resp;
    }

    public static Task toTaskFromRequest(TaskRequest request, User user) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        task.setUser(user);
        return task;
    }

    public static void updateTaskFromRequest(Task task, TaskRequest request) {
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
    }
}