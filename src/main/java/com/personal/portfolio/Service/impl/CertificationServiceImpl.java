package com.personal.portfolio.Service.impl;

import com.personal.portfolio.Dto.CertificationDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Mapper.CertificationMapper;
import com.personal.portfolio.Mapper.ProjectMapper;
import com.personal.portfolio.Model.Certification;
import com.personal.portfolio.Model.Project;
import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.CertificationRepository;
import com.personal.portfolio.Repository.UserRepository;
import com.personal.portfolio.Service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CertificationServiceImpl implements CertificationService {
    @Autowired
    CertificationRepository certificationRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public CertificationDTO addCertificate(Long userId, CertificationDTO certificationDTO) {
        Users users = userRepository.findById(userId)
                 .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        Certification certification = CertificationMapper.toEntity(certificationDTO);
        certification.setUser(users);

        // Using saveAndFlush to ensure immediate synchronization with the database
        Certification savedCertificatiom = certificationRepository.saveAndFlush(certification);

        return CertificationMapper.toDTO(savedCertificatiom);
    }

    @Override
    public CertificationDTO updateCerficateById(Long certificateId, CertificationDTO certificationDTO) {
        Certification certification = certificationRepository.findById(certificateId)
                .orElseThrow(() -> new ResourceNotFoundException("Certification", "ID", certificateId));

        // Update certification fields from DTO
        certification.setName(certificationDTO.getName());
        certification.setIssuingOrganization(certificationDTO.getIssuingOrganization());
        certification.setIssueDate(certificationDTO.getIssueDate());
        certification.setExpirationDate(certificationDTO.getExpirationDate());

        Certification updatedCertification = certificationRepository.save(certification);
        return CertificationMapper.toDTO(updatedCertification);
    }

    @Override
    public CertificationDTO getCertificateById(Long certificateId) {
        Certification certification = certificationRepository.findById(certificateId)
                .orElseThrow(() -> new ResourceNotFoundException("Certification", "ID", certificateId));
        return CertificationMapper.toDTO(certification);
    }

    @Override
    public List<CertificationDTO> getAllCertificatesByUser(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","ID",userId));
        List<Certification> certifications = certificationRepository.findAllByUser(user);
        List<CertificationDTO> certificationDTOList = new ArrayList<>();
        for (Certification cert : certifications) {
            certificationDTOList.add(CertificationMapper.toDTO(cert));
        }
        return certificationDTOList;
    }

    @Override
    public CertificationDTO deleteCertificateByUserId(Long certificateId) {
        // Find the certification by ID, or throw an exception if not found
        Certification certification = certificationRepository.findById(certificateId)
                .orElseThrow(() -> new ResourceNotFoundException("Certification", "ID", certificateId));

        // Delete the certification and return the deleted entity as DTO
        certificationRepository.delete(certification);
        return CertificationMapper.toDTO(certification);
    }
}

