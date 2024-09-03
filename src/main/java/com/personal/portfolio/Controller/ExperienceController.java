package com.personal.portfolio.Controller;

import com.personal.portfolio.Dto.ContactDTO;
import com.personal.portfolio.Dto.ExperienceDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Response.BaseResponse;
import com.personal.portfolio.Service.ExperienceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exp")
public class ExperienceController extends BaseController{


    @Autowired
    ExperienceService experienceService;


    private final Logger logger = LoggerFactory.getLogger(ExperienceController.class);

    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponse<ExperienceDTO>> addExperience(@PathVariable Long userId, @RequestBody ExperienceDTO experienceDTO, Authentication authentication){
        try{
            ExperienceDTO response = experienceService.addExperience(userId, experienceDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.CREATED).body(response),
                    authentication,
                    "Experience added successfully"
            );
        }catch (ResourceNotFoundException e){
            logger.error("User not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        }catch (Exception e) {
            logger.error("Failed to add contact: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Experience Add API failed"
            );
        }
    }

    // Update an existing contact
    @PutMapping("/{expId}")
    public ResponseEntity<BaseResponse<ExperienceDTO>> updateExperienceById(@PathVariable Long expId, @RequestBody ExperienceDTO experienceDTO, Authentication authentication) {
        try {
            ExperienceDTO response = experienceService.updateExperienceById(expId, experienceDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.ACCEPTED).body(response),
                    authentication,
                    "Experience updated successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("Experience not found for update: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to update experience: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Experience Update API failed"
            );
        }
    }

    // Get all contacts by user ID
    @GetMapping("/all/{userId}")
    public ResponseEntity<BaseResponse<List<ExperienceDTO>>> getAllExperiencesByUserId(@PathVariable Long userId, Authentication authentication) {
        try {
            List<ExperienceDTO> experienceList = experienceService.getAllExperiencesByUserId(userId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(experienceList),
                    authentication,
                    "Fetched all experience successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to fetch contacts: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Experience Fetch API failed"
            );
        }
    }

    // Remove a contact by ID
    @DeleteMapping("/{expId}")
    public ResponseEntity<BaseResponse<Void>> removeExperienceById(@PathVariable Long expId, Authentication authentication) {
        try {
            experienceService.removeExperienceById(expId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(null),
                    authentication,
                    "Experience removed successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("Experience not found for removal: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to remove experience: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Experience Remove API failed"
            );
        }
    }

    // Get a contact by contact ID
    @GetMapping("/{expId}")
    public ResponseEntity<BaseResponse<ExperienceDTO>> getExperienceById(@PathVariable Long expId, Authentication authentication) {
        try {
            ExperienceDTO response = experienceService.getExperienceById(expId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(response),
                    authentication,
                    "Fetched experience successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("Experience not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to fetch experience: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Experience Fetch API failed"
            );
        }
    }
}
