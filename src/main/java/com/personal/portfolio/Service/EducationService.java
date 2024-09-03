package com.personal.portfolio.Service;

import com.personal.portfolio.Dto.EducationDTO;
import com.personal.portfolio.Dto.ProjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EducationService {
    EducationDTO addEducation(Long userId, EducationDTO educationDTO);

    EducationDTO updateEducationById(Long eduId, EducationDTO educationDTO);

    void removeEducationById(Long eduId);

    List<EducationDTO> getAllEducationByUserId(Long userId);

    EducationDTO getEducationById(Long eduId);
}
