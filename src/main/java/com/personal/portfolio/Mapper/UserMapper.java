package com.personal.portfolio.Mapper;



import com.personal.portfolio.Dto.UserDTO;
import com.personal.portfolio.Model.Users;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(Users user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setProfilePicture(user.getProfilePicture());
        dto.setProjects(user.getProjects().stream().map(ProjectMapper::toDTO).collect(Collectors.toList()));
        dto.setEducationList(user.getEducationList().stream().map(EducationMapper::toDTO).collect(Collectors.toList()));
        dto.setExperienceList(user.getExperienceList().stream().map(ExperienceMapper::toDTO).collect(Collectors.toList()));
        dto.setCertifications(user.getCertifications().stream().map(CertificationMapper::toDTO).collect(Collectors.toList()));
        dto.setContacts(user.getContacts().stream().map(ContactMapper::toDTO).collect(Collectors.toList()));
        return dto;
    }

    public static Users toEntity(UserDTO dto) {
        Users user = new Users();
        user.setUserId(dto.getUserId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setBio(dto.getBio());
        user.setProfilePicture(dto.getProfilePicture());
        // Handle setting projects, education, experience, certifications, and contacts if necessary
        return user;
    }

}
