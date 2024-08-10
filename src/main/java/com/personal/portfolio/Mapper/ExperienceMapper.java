package com.personal.portfolio.Mapper;


import com.personal.portfolio.Dto.ExperienceDTO;
import com.personal.portfolio.Model.Experience;

public class ExperienceMapper {

    public static ExperienceDTO toDTO(Experience experience) {
        ExperienceDTO dto = new ExperienceDTO();
        dto.setExperienceId(experience.getExperienceId());
        dto.setCompanyName(experience.getCompanyName());
        dto.setPosition(experience.getPosition());
        dto.setStartDate(experience.getStartDate());
        dto.setEndDate(experience.getEndDate());
        dto.setDescription(experience.getDescription());
        return dto;
    }

    public static Experience toEntity(ExperienceDTO dto) {
        Experience experience = new Experience();
        experience.setExperienceId(dto.getExperienceId());
        experience.setCompanyName(dto.getCompanyName());
        experience.setPosition(dto.getPosition());
        experience.setStartDate(dto.getStartDate());
        experience.setEndDate(dto.getEndDate());
        experience.setDescription(dto.getDescription());
        return experience;
    }
}
