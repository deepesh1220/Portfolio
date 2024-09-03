package com.personal.portfolio.Service;

import com.personal.portfolio.Dto.ProjectDTO;
import com.personal.portfolio.Dto.SkillDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    ProjectDTO addProject(Long userId, ProjectDTO projectDTO);

    SkillDTO addSkillsOnProject(Long projectId, SkillDTO skillDTO);

    List<ProjectDTO> getAllProjectsByUser(Long userId);

    SkillDTO addSkill(SkillDTO skillDTO);

    ProjectDTO updateProjectById(Long projectId, ProjectDTO projectDTO);

    List<SkillDTO> getAllSkillsByUser(Long userId);
}
