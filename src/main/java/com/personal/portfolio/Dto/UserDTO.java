package com.personal.portfolio.Dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String bio;
    private String profilePicture;
    private List<ProjectDTO> projects;
    private List<EducationDTO> educationList;
    private List<ExperienceDTO> experienceList;
    private List<CertificationDTO> certifications;
    private List<ContactDTO> contacts;
}
