package com.example.tuan3.service;

import com.example.tuan3.dto.request.TaskRequestDTO;
import com.example.tuan3.dto.respone.TaskResponseDTO;
import com.example.tuan3.entity.*;
import com.example.tuan3.exception.CustomException;
import com.example.tuan3.repository.ProjectRepository;
import com.example.tuan3.repository.TaskRepository;
import com.example.tuan3.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final ProjectRepository projectRepo;
    private final UserRepo userRepo;

    public TaskService(TaskRepository taskRepo, ProjectRepository projectRepo, UserRepo userRepo){
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
    }

    // ===== Tạo task =====
    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO dto){
        Project project = projectRepo.findById(dto.getProjectId())
                .orElseThrow(() -> new CustomException("Project không tồn tại"));

        User assignedUser = null;
        if(dto.getAssignedUserId() != null){
            assignedUser = userRepo.findById(dto.getAssignedUserId())
                    .orElseThrow(() -> new CustomException("User không tồn tại"));

            // Kiểm tra user thuộc project
            boolean userBelongs = project.getUsers().stream()
                    .anyMatch(u -> u.getId().equals(dto.getAssignedUserId()));
            if(!userBelongs){
                throw new CustomException("User không thuộc project");
            }
        }

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .deadline(dto.getDeadline())
                .status(TaskStatus.TODO)
                .project(project)
                .assignedUser(assignedUser)
                .isActive(true)
                .build();

        task = taskRepo.save(task);

        return mapToDTO(task);
    }

    // ===== Assign task cho user =====
    @Transactional
    public TaskResponseDTO assignTask(Integer taskId, Integer userId){
        Task task = taskRepo.findTaskByIdAndIsActiveIsTrue(taskId)
                .orElseThrow(() -> new CustomException("Task không tồn tại"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new CustomException("User không tồn tại"));

        // Kiểm tra user thuộc project
        boolean userBelongs = task.getProject().getUsers().stream()
                .anyMatch(u -> u.getId().equals(userId));
        if(!userBelongs){
            throw new CustomException("User không thuộc project");
        }

        task.setAssignedUser(user);
        task = taskRepo.save(task);
        return mapToDTO(task);
    }

    // ===== Update status =====
    @Transactional
    public TaskResponseDTO updateStatus(Integer taskId, TaskStatus newStatus){
        Task task = taskRepo.findTaskByIdAndIsActiveIsTrue(taskId)
                .orElseThrow(() -> new CustomException("Task không tồn tại"));

        if(task.getStatus() == TaskStatus.DONE){
            throw new CustomException("Task đã DONE, không thể cập nhật");
        }

        task.setStatus(newStatus);
        task = taskRepo.save(task);
        return mapToDTO(task);
    }

    // ===== List task theo user =====
    public List<TaskResponseDTO> getTasksByUser(Integer userId){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new CustomException("User không tồn tại"));

        return taskRepo.findByAssignedUser(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ===== List task theo project =====
    public List<TaskResponseDTO> getTasksByProject(Integer projectId){
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new CustomException("Project không tồn tại"));

        return taskRepo.findByProject(project).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private TaskResponseDTO mapToDTO(Task task){
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .status(task.getStatus())
                .projectId(task.getProject().getId())
                .assignedUserId(task.getAssignedUser() != null ? task.getAssignedUser().getId() : null)
                .build();
    }
}