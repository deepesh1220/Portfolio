package com.personal.portfolio.Service;

import com.personal.portfolio.Dto.EducationDTO;
import com.personal.portfolio.Dto.ProjectDTO;
import org.springframework.stereotype.Service;

@Service
public interface EducationService {
    EducationDTO addEducation(Long userId, EducationDTO educationDTO);

    EducationDTO updateEducationById(Long eduId, EducationDTO educationDTO);

    void removeEducationById(Long eduId);
}
