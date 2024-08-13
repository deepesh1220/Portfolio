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

public class CertificationDTO {


    private Long certificationId;
    private String name;
    private String issuingOrganization;
    private Date issueDate;
    private Date expirationDate;

    // Getters and setters
}
