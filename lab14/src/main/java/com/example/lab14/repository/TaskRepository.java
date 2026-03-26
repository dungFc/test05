package com.example.lab14.repository;
import com.example.lab14.entity.Project;
import com.example.lab14.entity.Task;
import com.example.lab14.entity.TaskStatus;
import com.example.lab14.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    public List<Task> findByAssignedUserIdAndIsActiveTrue(@Param("id") Integer id);

    public List<Task> findByProjectIdAndIsActiveTrue(@Param("id") Integer id);

    public List<Task> findAllByIsActive(@Param("isActive") Boolean isActive);

    List<Task> findByAssignedUser(User user);

    List<Task> findByProject(Project project);

    List<Task> findByStatus(TaskStatus status);

    Optional<Task> findTaskByIdAndIsActiveIsTrue(Integer id);
}


