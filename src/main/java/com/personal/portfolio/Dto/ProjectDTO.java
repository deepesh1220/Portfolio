package com.personal.portfolio.Dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDTO {
//    Long projectId;
    String title;
    String description;
    Date startDate;
    Date endDate;
    String projectUrl;
    String repositoryUrl;
    List<SkillDTO> skills;

    // Getters and setters
}
