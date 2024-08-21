package com.personal.portfolio.Service.impl;

import com.personal.portfolio.Dto.ProjectDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Mapper.ProjectMapper;
import com.personal.portfolio.Model.Project;
import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.ProjectRepository;
import com.personal.portfolio.Repository.UserRepository;
import com.personal.portfolio.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ProjectDTO addProject(Long userId, ProjectDTO projectDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        Project project = ProjectMapper.toEntity(projectDTO);
        project.setUser(user);
        Project savedProject = projectRepository.save(project);
        ProjectDTO responseDto = ProjectMapper.toDTO(savedProject);
//        return "Project "+project.getTitle()+" Has Been Added on User-> "+ user.getName(); // Optionally return a response DTO or message instead
        return responseDto ;
    }
}
