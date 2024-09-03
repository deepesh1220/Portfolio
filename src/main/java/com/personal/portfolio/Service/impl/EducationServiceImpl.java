package com.personal.portfolio.Service.impl;

import com.personal.portfolio.Controller.ProjectController;
import com.personal.portfolio.Dto.EducationDTO;
import com.personal.portfolio.Dto.ProjectDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Mapper.EducationMapper;
import com.personal.portfolio.Mapper.ProjectMapper;
import com.personal.portfolio.Model.Education;
import com.personal.portfolio.Model.Project;
import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.EducationRepository;
import com.personal.portfolio.Repository.UserRepository;
import com.personal.portfolio.Service.EducationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(EducationServiceImpl.class);


    @Override
    public EducationDTO addEducation(Long userId, EducationDTO educationDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        Education education = EducationMapper.toEntity(educationDTO);
        education.setUser(user);

        // Using saveAndFlush to ensure immediate synchronization with the database
        Education savedEdu = educationRepository.saveAndFlush(education);

        return EducationMapper.toDTO(savedEdu);
    }

    @Override
    public EducationDTO updateEducationById(Long eduId, EducationDTO educationDTO) {
        Education education = educationRepository.findById(eduId).get();
        education.setInstitutionName(educationDTO.getInstitutionName());
        education.setDegree(educationDTO.getDegree());
        education.setFieldOfStudy(educationDTO.getFieldOfStudy());
        education.setStartDate(educationDTO.getStartDate());
        education.setEndDate(educationDTO.getEndDate());
        Education response = educationRepository.saveAndFlush(education);
        return EducationMapper.toDTO(response);
    }

    @Override
    public void removeEducationById(Long eduId) throws ResourceNotFoundException {
        try {
            // Check if the education entry exists
            if (!educationRepository.existsById(eduId)) {
                throw new ResourceNotFoundException("Education entry not found with ","id: " , eduId);
            }

            // Delete the education entry
            educationRepository.deleteById(eduId);

        } catch (ResourceNotFoundException e) {
            logger.error("Education entry not found for removal: {}", e.getMessage());
            throw e; // Re-throw the exception for further handling
        } catch (Exception e) {
            logger.error("Failed to remove education entry: {}", e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred while removing the education entry.", e);
        }
    }

    @Override
    public List<EducationDTO> getAllEducationByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User ","ID: " , userId);
        }
        Users users = userRepository.findById(userId).get();
        List<Education> educations = educationRepository.findAllByUser(users);

        return educations.stream()
                .map(EducationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EducationDTO getEducationById(Long eduId) {
        // Fetch the education entry by its ID or throw an exception if not found
        Education education = educationRepository.findById(eduId)
                .orElseThrow(() -> new ResourceNotFoundException("Education ", "ID", eduId));

        // Convert the education entity to a DTO and return it
        return EducationMapper.toDTO(education);
    }
}
