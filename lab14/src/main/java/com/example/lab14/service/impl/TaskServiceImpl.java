package com.example.lab14.service.impl;

import com.example.lab14.dto.request.TaskRequestCreate;
import com.example.lab14.dto.request.TaskRequestUpdate;
import com.example.lab14.dto.response.TaskResponse;
import com.example.lab14.entity.Project;
import com.example.lab14.entity.Task;
import com.example.lab14.entity.TaskStatus;
import com.example.lab14.entity.User;
import com.example.lab14.exception.BadRequestException;
import com.example.lab14.exception.ResourceNotFoundException;
import com.example.lab14.repository.ProjectRepository;
import com.example.lab14.repository.TaskRepository;
import com.example.lab14.repository.UserRepository;
import com.example.lab14.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    // GET

    @Override
    public List<TaskResponse> findAllTask() {
        return taskRepository.findAllByIsActive(true)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<TaskResponse> findAllTaskIsActive(Boolean isActive) {
        return taskRepository.findAllByIsActive(isActive)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public TaskResponse findTaskById(Integer id) {
        Task task = taskRepository.findTaskByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found or inactive"));
        return toResponse(task);
    }

    @Override
    public List<TaskResponse> getTasksByUser(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return taskRepository.findByAssignedUserIdAndIsActiveTrue(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<TaskResponse> getTasksByProject(Integer projectId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        return taskRepository.findByProjectIdAndIsActiveTrue(projectId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<TaskResponse> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .filter(Task::getIsActive)
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<TaskResponse> getTasksByStatus(TaskStatus taskStatus) {
        return findByStatus(taskStatus);
    }

    // CREATE

    @Override
    public TaskResponse createTask(TaskRequestCreate request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        User user = null;
        if (request.getAssignedUserId() != null) {
            user = userRepository.findById(request.getAssignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));


            if (!project.getUsers().contains(user)) {
                throw new BadRequestException("User not in project");
            }
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO);
        task.setProject(project);
        task.setAssignedUser(user);
        task.setIsActive(request.getActive() != null ? request.getActive() : true);

        taskRepository.save(task);

        return toResponse(task);
    }

    // UPDATE

    @Override
    public TaskResponse updateTask(TaskRequestUpdate request, Integer id) {

        Task task = taskRepository.findTaskByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found or inactive"));


        if (task.getStatus() == TaskStatus.DONE) {
            throw new BadRequestException("Cannot update DONE task");
        }

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getDeadline() != null) {
            task.setDeadline(request.getDeadline());
        }

        taskRepository.save(task);
        return toResponse(task);
    }

    // STATUS

    @Override
    public TaskResponse updateStatus(TaskStatus newStatus, Integer id) {

        Task task = taskRepository.findTaskByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found or inactive"));

        validateStatusTransition(task.getStatus(), newStatus);

        task.setStatus(newStatus);
        taskRepository.save(task);

        return toResponse(task);
    }

    private void validateStatusTransition(TaskStatus current, TaskStatus next) {

        if (current == TaskStatus.DONE) {
            throw new BadRequestException("Cannot update DONE task");
        }

        if (current == TaskStatus.TODO && next == TaskStatus.DONE) {
            throw new BadRequestException("Must go through IN_PROGRESS");
        }

        if (current == TaskStatus.IN_PROGRESS && next == TaskStatus.TODO) {
            throw new BadRequestException("Cannot move backward");
        }
    }

    // ACTIVE

    @Override
    public TaskResponse updateActive(Boolean isActive, Integer id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.setIsActive(isActive);
        taskRepository.save(task);

        return toResponse(task);
    }

    // ASSIGN

    @Override
    public TaskResponse asignUser(Integer taskId, Integer userId) {

        Task task = taskRepository.findTaskByIdAndIsActiveIsTrue(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found or inactive"));

        if (task.getAssignedUser() != null) {
            throw new BadRequestException("Task already assigned");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!task.getProject().getUsers().contains(user)) {
            throw new BadRequestException("User not in project");
        }

        task.setAssignedUser(user);
        taskRepository.save(task);

        return toResponse(task);
    }

    // MAPPER

    private TaskResponse toResponse(Task task) {
        return modelMapper.map(task, TaskResponse.class);
    }
}
