package com.example.lab14.repository;

import com.example.lab14.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByIsActive(Boolean isActive);

}
