package com.personal.portfolio.Service.impl;

import com.personal.portfolio.Dto.ExperienceDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Mapper.ContactMapper;
import com.personal.portfolio.Mapper.ExperienceMapper;
import com.personal.portfolio.Model.Contact;
import com.personal.portfolio.Model.Experience;
import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.ExperienceRepository;
import com.personal.portfolio.Repository.UserRepository;
import com.personal.portfolio.Service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExperienceRepository experienceRepository;

    @Override
    public ExperienceDTO addExperience(Long userId, ExperienceDTO experienceDTO) {
        // Find user by ID or throw exception if not found
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        // Map DTO to entity
        Experience experience = ExperienceMapper.toEntity(experienceDTO);
        experience.setUser(user);  // Set user for the contact

        // Save and return the mapped DTO
        Experience savedExperience = experienceRepository.saveAndFlush(experience);
        return ExperienceMapper.toDTO(savedExperience);
    }

    @Override
    public ExperienceDTO updateExperienceById(Long expId, ExperienceDTO experienceDTO) {
        Experience experience = experienceRepository.findById(expId)
                .orElseThrow(()-> new ResourceNotFoundException("Experience","ID",expId));

        experience.setCompanyName(experienceDTO.getCompanyName());
        experience.setPosition(experienceDTO.getPosition());
        experience.setStartDate(experienceDTO.getStartDate());
        experience.setEndDate(experienceDTO.getEndDate());
        experience.setDescription(experienceDTO.getDescription());

        // Save updated experience and map to DTO
        Experience updatedExperience = experienceRepository.saveAndFlush(experience);
        return ExperienceMapper.toDTO(updatedExperience);
    }

    @Override
    public List<ExperienceDTO> getAllExperiencesByUserId(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","ID",userId));

        // Fetch experiences by user
        List<Experience> experiences = experienceRepository.findAllByUser(user);

        return experiences.stream()
                .map(ExperienceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeExperienceById(Long expId) {
        // Check if experience exists before deletion
        if(!experienceRepository.existsById(expId)){
            throw new ResourceNotFoundException("Experience", "ID", expId);
        }
        //Remove experience
        experienceRepository.deleteById(expId);
    }

    @Override
    public ExperienceDTO getExperienceById(Long expId) {
        // Find experience by ID or throw exception if not found
        Experience experience = experienceRepository.findById(expId)
                .orElseThrow(()-> new ResourceNotFoundException("Experience", "ID", expId));
        // Map experience to DTO
        return ExperienceMapper.toDTO(experience);
    }


}
