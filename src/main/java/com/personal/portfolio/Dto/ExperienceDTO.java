package com.personal.portfolio.Dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExperienceDTO {
    private Long experienceId;
    private String companyName;
    private String position;
    private Date startDate;
    private Date endDate;
    private String description;

    // Getters and setters
}
