package com.personal.portfolio.Service;


import com.personal.portfolio.Dto.ContactDTO;
import com.personal.portfolio.Dto.ExperienceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExperienceService {
    ExperienceDTO addExperience(Long userId, ExperienceDTO experienceDTO);

    ExperienceDTO updateExperienceById(Long expId, ExperienceDTO experienceDTO);


    List<ExperienceDTO> getAllExperiencesByUserId(Long userId);

    void removeExperienceById(Long expId);


    ExperienceDTO getExperienceById(Long exeId);
}
