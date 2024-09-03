package com.personal.portfolio.Controller;

import com.personal.portfolio.Auth.Security.Jwt.JwtUtil;
import com.personal.portfolio.Auth.Service.UserDetailsServiceImpl;
import com.personal.portfolio.Dto.CertificationDTO;
import com.personal.portfolio.Dto.ProjectDTO;
import com.personal.portfolio.Dto.SkillDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Model.Certification;
import com.personal.portfolio.Response.BaseResponse;
import com.personal.portfolio.Service.CertificationService;
import com.personal.portfolio.Service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/certifications")
public class CertificationController extends BaseController {


    @Autowired
    CertificationService certificationService;


    private final Logger logger = LoggerFactory.getLogger(CertificationController.class);


    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponse<CertificationDTO>> addCertificate(@PathVariable Long userId, @RequestBody CertificationDTO certificationDTO, Authentication authentication) {
        try {
            CertificationDTO response = certificationService.addCertificate(userId, certificationDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.CREATED).body(response),
                    authentication,
                    "Certificate added successfully"
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
                    "Certificate Add API failed"
            );
        }
    }

    @PutMapping("/{certificateId}")
    public ResponseEntity<BaseResponse<CertificationDTO>> updateCerficateById(@PathVariable Long certificateId, @RequestBody CertificationDTO certificationDTO, Authentication authentication) {
        try {
            CertificationDTO response = certificationService.updateCerficateById(certificateId, certificationDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.ACCEPTED).body(response),
                    authentication,
                    "Certificate updated successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("Certificate not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to update certificate: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Certificate Update API failed"
            );
        }
    }


    @GetMapping("/{certificateId}")
    public ResponseEntity<BaseResponse<CertificationDTO>> getCertificateById(@RequestParam Long certificateId, Authentication authentication) {
        try {
            CertificationDTO certificate = certificationService.getCertificateById(certificateId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(certificate),
                    authentication,
                    "Certificate retrieved successfully"
            );
        } catch (Exception e) {
            logger.error("Failed to retrieve certificate: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Get certificate API failed"
            );
        }
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<BaseResponse<List<CertificationDTO>>> getAllCertificatesByUser(@PathVariable Long userId, Authentication authentication) {
        try {
            List<CertificationDTO> certificateList = certificationService.getAllCertificatesByUser(userId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(certificateList),
                    authentication,
                    "Certificate retrieved successfully"
            );
        } catch (Exception e) {
            logger.error("Failed to retrieve certificate: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Get all Certificate API failed"
            );
        }
    }
        @DeleteMapping("/{certificateId}")
        public ResponseEntity<BaseResponse<CertificationDTO>> deleteCertificateByUserId (@PathVariable  Long certificateId, Authentication authentication) {
            try {
                CertificationDTO certificate = certificationService.deleteCertificateByUserId(certificateId);
                return withNewAccessToken(
                        ResponseEntity.status(HttpStatus.OK).body(certificate),
                        authentication,
                        "Certificate retrieved successfully"
                );
            } catch (Exception e) {
                logger.error("Failed to retrieve certificate: {}", e.getMessage(), e);
                return withNewAccessToken(
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                        authentication,
                        "Get certificate API failed"
                );
            }

        }
}


