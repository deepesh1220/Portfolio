package com.personal.portfolio.Mapper;


import com.personal.portfolio.Dto.CertificationDTO;
import com.personal.portfolio.Model.Certification;

public class CertificationMapper {

    public static CertificationDTO toDTO(Certification certification) {
        CertificationDTO dto = new CertificationDTO();
        dto.setCertificationId(certification.getCertificationId());
        dto.setName(certification.getName());
        dto.setIssuingOrganization(certification.getIssuingOrganization());
        dto.setIssueDate(certification.getIssueDate());
        dto.setExpirationDate(certification.getExpirationDate());
        return dto;
    }

    public static Certification toEntity(CertificationDTO dto) {
        Certification certification = new Certification();
        certification.setCertificationId(dto.getCertificationId());
        certification.setName(dto.getName());
        certification.setIssuingOrganization(dto.getIssuingOrganization());
        certification.setIssueDate(dto.getIssueDate());
        certification.setExpirationDate(dto.getExpirationDate());
        return certification;
    }
}
