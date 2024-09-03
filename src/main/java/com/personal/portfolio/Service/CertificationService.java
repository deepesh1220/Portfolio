package com.personal.portfolio.Service;


import com.personal.portfolio.Dto.CertificationDTO;
import com.personal.portfolio.Model.Certification;
import com.personal.portfolio.Repository.CertificationRepository;
import com.personal.portfolio.Service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface CertificationService {


    CertificationDTO addCertificate(Long userId, CertificationDTO certificationDTO);

    CertificationDTO updateCerficateById(Long certificateId, CertificationDTO certificationDTO);

    CertificationDTO getCertificateById(Long certificateId);

    List<CertificationDTO> getAllCertificatesByUser(Long userId);

    CertificationDTO deleteCertificateByUserId(Long certificateId);
}

