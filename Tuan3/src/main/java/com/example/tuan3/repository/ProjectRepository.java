package com.example.tuan3.repository;

import com.example.tuan3.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByIsActive(Boolean isActive);

}