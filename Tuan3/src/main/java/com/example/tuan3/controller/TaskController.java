package com.example.tuan3.controller;

import com.example.tuan3.dto.request.TaskRequestDTO;
import com.example.tuan3.dto.respone.TaskResponseDTO;
import com.example.tuan3.exception.ApiResponse;
import com.example.tuan3.entity.TaskStatus;
import com.example.tuan3.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    // ===== Tạo task =====
    @PostMapping
    public ApiResponse<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO request){
        TaskResponseDTO task = taskService.createTask(request);
        return new ApiResponse<>(200, "Task created successfully", task);
    }

    // ===== Assign task =====
    @PutMapping("/{taskId}/assign/{userId}")
    public ApiResponse<TaskResponseDTO> assignTask(@PathVariable Integer taskId, @PathVariable Integer userId){
        TaskResponseDTO task = taskService.assignTask(taskId, userId);
        return new ApiResponse<>(200, "Task assigned successfully", task);
    }

    // ===== Update status =====
    @PutMapping("/{taskId}/status")
    public ApiResponse<TaskResponseDTO> updateStatus(@PathVariable Integer taskId, @RequestParam TaskStatus status){
        TaskResponseDTO task = taskService.updateStatus(taskId, status);
        return new ApiResponse<>(200, "Task status updated successfully", task);
    }

    // ===== List task theo user =====
    @GetMapping("/user/{userId}")
    public ApiResponse<List<TaskResponseDTO>> getTasksByUser(@PathVariable Integer userId){
        List<TaskResponseDTO> tasks = taskService.getTasksByUser(userId);
        return new ApiResponse<>(200, "Tasks retrieved successfully", tasks);
    }

    // ===== List task theo project =====
    @GetMapping("/project/{projectId}")
    public ApiResponse<List<TaskResponseDTO>> getTasksByProject(@PathVariable Integer projectId){
        List<TaskResponseDTO> tasks = taskService.getTasksByProject(projectId);
        return new ApiResponse<>(200, "Tasks retrieved successfully", tasks);
    }
}