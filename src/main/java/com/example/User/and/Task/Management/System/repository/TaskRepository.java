package com.example.User.and.Task.Management.System.repository;

import com.example.User.and.Task.Management.System.model.Task;
import com.example.User.and.Task.Management.System.model.TaskStatus;
import com.example.User.and.Task.Management.System.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user, Sort sort);
    List<Task> findByUserAndStatus(User user, TaskStatus status, Sort sort);
    List<Task> findByUser(User user);
}