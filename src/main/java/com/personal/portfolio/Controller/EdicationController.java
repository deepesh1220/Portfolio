package com.personal.portfolio.Controller;

import com.personal.portfolio.Dto.EducationDTO;
import com.personal.portfolio.Dto.ProjectDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Response.BaseResponse;
import com.personal.portfolio.Service.EducationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EdicationController extends BaseController{


    @Autowired
    EducationService educationService;

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);



    @PostMapping("/edu/{userId}")
    public ResponseEntity<BaseResponse<EducationDTO>> addEducation(@PathVariable Long userId, @RequestBody EducationDTO educationDTO, Authentication authentication) {
        try {
            EducationDTO response = educationService.addEducation(userId, educationDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.CREATED).body(response),
                    authentication,
                    "Education added successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to add education: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Education Add API failed"
            );
        }
    }

    @PutMapping("/edu/{eduId}")
    public ResponseEntity<BaseResponse<EducationDTO>> updateEducationById(@PathVariable Long eduId, @RequestBody EducationDTO educationDTO, Authentication authentication) {
        try {
            EducationDTO response = educationService.updateEducationById(eduId, educationDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.ACCEPTED).body(response),
                    authentication,
                    "Education updated successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("Those Perticuler Education not found for update: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to update education: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Education Update API failed"
            );
        }
    }

        @DeleteMapping("/edu/{eduId}")
        public ResponseEntity<BaseResponse<Void>> removeEducationById(@PathVariable Long eduId, Authentication authentication) {
            try {
                educationService.removeEducationById(eduId);
                return withNewAccessToken(
                        ResponseEntity.status(HttpStatus.OK).body(null),
                        authentication,
                        "Education removed successfully"
                );
            } catch (ResourceNotFoundException e) {
                logger.error("Education not found for removal: {}", e.getMessage());
                return withNewAccessToken(
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                        authentication,
                        e.getMessage()
                );
            } catch (Exception e) {
                logger.error("Failed to remove education: {}", e.getMessage(), e);
                return withNewAccessToken(
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                        authentication,
                        "Education Remove API failed"
                );
            }
        }


    }
