package mapper;

import dto.request.TaskRequest;
import dto.response.TaskResponse;
import dto.response.UserResponse;
import com.example.User.and.Task.Management.System.model.Task;
import com.example.User.and.Task.Management.System.model.TaskStatus;
import com.example.User.and.Task.Management.System.model.User;
import java.util.Set;
import java.util.stream.Collectors;

public class DtoMapper {
    public static TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
    }

    public static UserResponse toUserResponse(User user) {
        UserResponse resp = new UserResponse();
        resp.setId(user.getId());
        resp.setUsername(user.getUsername());
        resp.setRoles(user.getRoles());
        return resp;
    }
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
