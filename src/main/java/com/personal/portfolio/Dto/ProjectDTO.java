package com.personal.portfolio.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProjectDTO {
    Long projectId;
    String title;
    String description;
    Date startDate;
    Date endDate;
    String projectUrl;
    String repositoryUrl;
    List<SkillDTO> skills;

    // Getters and setters
}
