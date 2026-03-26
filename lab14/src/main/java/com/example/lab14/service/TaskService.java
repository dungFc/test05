package com.example.lab14.service;
import com.example.lab14.dto.request.TaskRequestCreate;
import com.example.lab14.dto.request.TaskRequestUpdate;
import com.example.lab14.dto.response.TaskResponse;
import com.example.lab14.entity.TaskStatus;

import java.util.List;

public interface TaskService {
    List<TaskResponse> findAllTask();

    List<TaskResponse> findAllTaskIsActive(Boolean isActive);

    TaskResponse findTaskById(Integer id);

    TaskResponse createTask(TaskRequestCreate taskRequestCreate);

    TaskResponse updateTask(TaskRequestUpdate taskRequestUpdate, Integer id);

//    void deleteTask(Integer id);



    TaskResponse updateStatus(TaskStatus taskStatus, Integer id);

    TaskResponse updateActive(Boolean isActive, Integer id);

    List<TaskResponse> getTasksByUser(Integer userId);

    List<TaskResponse> getTasksByProject(Integer projectId);

    List<TaskResponse> getTasksByStatus(TaskStatus taskStatus);

    List<TaskResponse> findByStatus(TaskStatus status);

    TaskResponse asignUser(Integer taskId, Integer userId);
}

