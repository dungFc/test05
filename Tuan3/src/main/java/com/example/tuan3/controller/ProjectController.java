package com.example.tuan3.controller;

import com.example.tuan3.dto.request.ProjectRequestDTO;
import com.example.tuan3.dto.respone.ProjectResponseDTO;
import com.example.tuan3.exception.ApiResponse;
import com.example.tuan3.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping("/{creatorId}")
    public ApiResponse<ProjectResponseDTO> createProject(
            @PathVariable Integer creatorId,
            @Valid @RequestBody ProjectRequestDTO request){
        ProjectResponseDTO project = projectService.createProject(request, creatorId);
        return new ApiResponse<>(200, "Project created successfully", project);
    }

    @GetMapping
    public ApiResponse<List<ProjectResponseDTO>> getAllProjects(){
        List<ProjectResponseDTO> projects = projectService.getAllProjects();
        return new ApiResponse<>(200, "Projects retrieved successfully", projects);
    }
}
