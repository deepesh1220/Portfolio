package com.personal.portfolio.Controller;

import com.personal.portfolio.Dto.EducationDTO;
import com.personal.portfolio.Dto.ProjectDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Response.BaseResponse;
import com.personal.portfolio.Service.EducationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/edu")
public class EdicationController extends BaseController {


    @Autowired
    EducationService educationService;

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);


    @Operation(summary = "Add Education", description = "Add a new education entry for a user", tags = { "Education API" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Education added successfully",
                    content = @Content(schema = @Schema(implementation = EducationDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/{userId}")
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

    @Operation(summary = "Update Education", description = "Update an existing education entry by ID", tags = { "Education API" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Education updated successfully",
                    content = @Content(schema = @Schema(implementation = EducationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Education not found for update",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("/{eduId}")
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

    @Operation(summary = "Remove Education by ID", description = "Remove an education entry by its ID", tags = { "Education API" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Education removed successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Education not found for removal",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @DeleteMapping("/{eduId}")
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


    @Operation(summary = "Get All Education by User ID", description = "Retrieve all education entries for a specific user", tags = { "Education API" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all education entries successfully",
                    content = @Content(schema = @Schema(implementation = EducationDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/all/{userId}")
    public ResponseEntity<BaseResponse<List<EducationDTO>>> getAllEducationByUserId(@PathVariable Long userId, Authentication authentication) {
        try {
            List<EducationDTO> educationList = educationService.getAllEducationByUserId(userId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(educationList),
                    authentication,
                    "Fetched all education entries successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to fetch education entries: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Education Fetch API failed"
            );
        }
    }


    @Operation(summary = "Get Education by ID", description = "Retrieve an education entry by its ID", tags = { "Education API" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched education entry successfully",
                    content = @Content(schema = @Schema(implementation = EducationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Education not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/{eduId}")
    public ResponseEntity<BaseResponse<EducationDTO>> getEducationById(@PathVariable Long eduId, Authentication authentication) {
        try {
            EducationDTO educationDTO = educationService.getEducationById(eduId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(educationDTO),
                    authentication,
                    "Fetched education entry successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("Education not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to fetch education entry: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Education Fetch API failed"
            );
        }
    }

}
