package com.example.lab14.controller;

import com.example.lab14.dto.request.TaskRequestCreate;
import com.example.lab14.dto.request.TaskRequestUpdate;
import com.example.lab14.dto.response.TaskResponse;
import com.example.lab14.entity.TaskStatus;
import com.example.lab14.exception.ApiResponse;
import com.example.lab14.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> findAll() {
        return ResponseEntity.ok(
                ApiResponse.success(taskService.findAllTask())
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ApiResponse.success(taskService.findTaskById(id))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @Valid @RequestBody TaskRequestCreate task
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(taskService.createTask(task)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable Integer id,
            @Valid @RequestBody TaskRequestUpdate task
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(taskService.updateTask(task, id))
        );
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateStatus(
            @PathVariable Integer id,
            @RequestBody TaskRequestUpdate task
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(taskService.updateStatus(task.getStatus(), id))
        );
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateActive(
            @PathVariable Integer id,
            @RequestBody TaskRequestUpdate task
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(taskService.updateActive(task.getActive(), id))
        );
    }

    @GetMapping("/assign/{userId}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> findAllByUserId(
            @PathVariable Integer userId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(taskService.getTasksByUser(userId))
        );
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> findAllByProjectId(
            @PathVariable Integer projectId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(taskService.getTasksByProject(projectId))
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> findAllByStatus(
            @PathVariable TaskStatus status
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(taskService.findByStatus(status))
        );
    }

    @PutMapping("{taskId}/asign-user/{userId}")
    public ResponseEntity<ApiResponse<TaskResponse>> assignTask(
            @PathVariable Integer taskId,
            @PathVariable Integer userId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(taskService.asignUser(taskId, userId))
        );
    }
}
