package com.personal.portfolio.Controller;

import com.personal.portfolio.Dto.ProjectDTO;
import com.personal.portfolio.Dto.SkillDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Response.BaseResponse;
import com.personal.portfolio.Service.ProjectService;
import com.personal.portfolio.Auth.Security.Jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController extends BaseController{

    @Autowired
    ProjectService projectService;

    @Autowired
    private JwtUtil jwtUtil;


    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);


    @PostMapping("/project/{userId}")
    public ResponseEntity<BaseResponse<ProjectDTO>> addProject(@PathVariable Long userId, @RequestBody ProjectDTO projectDTO, Authentication authentication) {
        try {
            ProjectDTO response = projectService.addProject(userId, projectDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.CREATED).body(response),
                    authentication,
                    "Project added successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to add project: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Project Add API failed"
            );
        }
    }

    @PutMapping("/project/{projectId}")
    public ResponseEntity<BaseResponse<ProjectDTO>> updateProjectById(@PathVariable Long projectId, @RequestBody ProjectDTO projectDTO, Authentication authentication) {
        try {
            ProjectDTO response = projectService.updateProjectById(projectId, projectDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.ACCEPTED).body(response),
                    authentication,
                    "Project updated successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("Project not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to update project: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Project Update API failed"
            );
        }
    }


    @PostMapping("/project/skill/{projectId}")
    public ResponseEntity<BaseResponse<SkillDTO>> addSkillsOnProject(@PathVariable Long projectId, @RequestBody SkillDTO skillDTO, Authentication authentication) {
        try {
            SkillDTO response = projectService.addSkillsOnProject(projectId, skillDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.CREATED).body(response),
                    authentication,
                    "Skill added to project successfully"
            );
        } catch (Exception e) {
            logger.error("Failed to add skill to project: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Add Skills API failed"
            );
        }
    }

    @GetMapping("/project/all-projects")
    public ResponseEntity<BaseResponse<List<ProjectDTO>>> getAllProjectsByUser(@RequestParam Long userId, Authentication authentication) {
        try {
            List<ProjectDTO> projectList = projectService.getAllProjectsByUser(userId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(projectList),
                    authentication,
                    "Projects retrieved successfully"
            );
        } catch (Exception e) {
            logger.error("Failed to retrieve projects: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Get all projects API failed"
            );
        }
    }


    @PostMapping("/skill")
    public ResponseEntity<BaseResponse<SkillDTO>> addSkill(@RequestBody SkillDTO skillDTO, Authentication authentication) {
        try {
            SkillDTO createdSkill = projectService.addSkill(skillDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.CREATED).body(createdSkill),
                    authentication,
                    "Skill added successfully"
            );
        } catch (Exception e) {
            logger.error("Failed to add skill: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Add Skill API failed"
            );
        }
    }

    @GetMapping("/skill/{userId}")
    public ResponseEntity<BaseResponse<List<SkillDTO>>> getAllSkillsByUser(@PathVariable Long userId, Authentication authentication){
        try{
            List<SkillDTO> skillList = projectService.getAllSkillsByUser(userId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(skillList),
                    authentication,
                    "Skills retrieved successfully"
            );
        }catch (ResourceNotFoundException e) {
            logger.error("Skill not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to skill load: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Get Skills API failed"
            );
        }
    }

}
