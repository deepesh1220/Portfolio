package com.personal.portfolio.Mapper;



import com.personal.portfolio.Dto.ProjectDTO;
import com.personal.portfolio.Model.Project;

import java.util.stream.Collectors;

public class ProjectMapper {

    public static ProjectDTO toDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setProjectId(project.getProjectId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setProjectUrl(project.getProjectUrl());
        dto.setRepositoryUrl(project.getRepositoryUrl());
        dto.setSkills(project.getSkills().stream().map(SkillMapper::toDTO).collect(Collectors.toList()));
        return dto;
    }

    public static Project toEntity(ProjectDTO dto) {
        Project project = new Project();
        project.setProjectId(dto.getProjectId());
        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setProjectUrl(dto.getProjectUrl());
        project.setRepositoryUrl(dto.getRepositoryUrl());
        // Handle setting skills if necessary
        return project;
    }
}
