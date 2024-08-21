package com.personal.portfolio.Controller;


import com.personal.portfolio.Auth.Controller.AuthController;
import com.personal.portfolio.Dto.ProjectDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Model.Project;
import com.personal.portfolio.Service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ProjectController {


    @Autowired
    ProjectService projectService;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("project/{userId}")
    public ResponseEntity<?> addProject(@PathVariable Long userId, @RequestBody ProjectDTO projectDTO) {
        try {
            ProjectDTO response = projectService.addProject(userId, projectDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("API failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Project Add API failed");
        }
    }



}
