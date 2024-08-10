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
    private Long projectId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String projectUrl;
    private String repositoryUrl;
    private List<SkillDTO> skills;

    // Getters and setters
}
