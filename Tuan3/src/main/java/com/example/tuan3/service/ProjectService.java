package com.example.tuan3.service;

import com.example.tuan3.dto.request.ProjectRequestDTO;
import com.example.tuan3.dto.respone.ProjectResponseDTO;
import com.example.tuan3.entity.Project;
import com.example.tuan3.entity.User;
import com.example.tuan3.exception.CustomException;
import com.example.tuan3.repository.ProjectRepository;
import com.example.tuan3.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepo;
    private final UserRepo userRepo;

    public ProjectService(ProjectRepository projectRepo, UserRepo userRepo){
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public ProjectResponseDTO createProject(ProjectRequestDTO dto, Integer creatorId){
        User creator = userRepo.findById(creatorId)
                .orElseThrow(() -> new CustomException("User không tồn tại"));

        Project project = Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .createdBy(creator)
                .isActive(true)
                .build();

        project = projectRepo.save(project);

        return mapToDTO(project);
    }

    public List<ProjectResponseDTO> getAllProjects(){
        return projectRepo.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ProjectResponseDTO mapToDTO(Project project){
        return ProjectResponseDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdById(project.getCreatedBy() != null ? project.getCreatedBy().getId() : null)
                .build();
    }
}