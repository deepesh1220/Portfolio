package com.personal.portfolio.Service.impl;

import com.personal.portfolio.Dto.ProjectDTO;
import com.personal.portfolio.Dto.SkillDTO;
import com.personal.portfolio.Exception.ResourceAlreadyExistsException;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Mapper.ProjectMapper;
import com.personal.portfolio.Mapper.SkillMapper;
import com.personal.portfolio.Model.Project;
import com.personal.portfolio.Model.Skill;
import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.ProjectRepository;
import com.personal.portfolio.Repository.SkillRepository;
import com.personal.portfolio.Repository.UserRepository;
import com.personal.portfolio.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ProjectDTO addProject(Long userId, ProjectDTO projectDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        Project project = ProjectMapper.toEntity(projectDTO);
        project.setUser(user);

        // Using saveAndFlush to ensure immediate synchronization with the database
        Project savedProject = projectRepository.saveAndFlush(project);

        return ProjectMapper.toDTO(savedProject);
    }

    @Override
    public SkillDTO addSkillsOnProject(Long projectId, SkillDTO skillDTO) {
        // Retrieve the project by its ID
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "ID", projectId));

        // Map the SkillDTO to a Skill entity
        Skill skill = skillRepository.findByNameAndCategory(skillDTO.getName(), skillDTO.getCategory())
                .orElseGet(() -> SkillMapper.toEntity(skillDTO)); // Create a new Skill if it doesn't exist

        // Initialize the projects list if it's null
        if (skill.getProjects() == null) {
            skill.setProjects(new ArrayList<>());
        }
        // Add the project to the skill's projects list
        skill.getProjects().add(project);

        // Ensure the project also has this skill in its skill list
        if (project.getSkills() == null) {
            project.setSkills(new ArrayList<>());
        }
        project.getSkills().add(skill);


        // Save both entities
        skillRepository.save(skill);
        projectRepository.save(project);

        return SkillMapper.toDTO(skill);
    }

    @Override
    public List<ProjectDTO> getAllProjectsByUser(Long userId) {
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        List<Project> projects = userRepository.findById(userId).get().getProjects();
        if(projects == null){
            return new ArrayList<>();
        }
        for (Project p: projects) {
            projectDTOList.add(ProjectMapper.toDTO(p));
        }
        return projectDTOList;
    }

    @Override
    public SkillDTO addSkill(SkillDTO skillDTO) {
        Skill skill = SkillMapper.toEntity(skillDTO);
        Optional<Skill> existingSkill = skillRepository.findByName(skillDTO.getName());
        if (existingSkill.isPresent()) {
            throw new ResourceAlreadyExistsException("Skill already exists");
        }
        Skill savedSkill = skillRepository.save(skill);
        return SkillMapper.toDTO(savedSkill);

    }

    @Override
    public ProjectDTO updateProjectById(Long projectId, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "ID", projectId));
        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setRepositoryUrl(projectDTO.getRepositoryUrl());
        project.setProjectUrl(projectDTO.getProjectUrl());

        Project updateProject = projectRepository.saveAndFlush(project);
        return ProjectMapper.toDTO(updateProject);

    }

    @Override
    public List<SkillDTO> getAllSkillsByUser(Long userId) {
        Set<SkillDTO> skillDTOList = new HashSet<>();
        List<Project> projects = userRepository.findById(userId).get().getProjects();
        List<Skill> skills = new ArrayList<>();
        if(projects == null){
            return new ArrayList<>();
        }
        for (Project p: projects) {
            for(Skill s: p.getSkills()){
                skillDTOList.add(SkillMapper.toDTO(s));
            }
        }
        return skillDTOList.stream().collect(Collectors.toList());
    }
}
