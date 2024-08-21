package com.personal.portfolio.Service;

import com.personal.portfolio.Dto.ProjectDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    ProjectDTO addProject(Long userId, ProjectDTO projectDTO);
}
