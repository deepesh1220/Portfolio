package com.personal.portfolio.Service;

import com.personal.portfolio.Dto.ContactDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {
    ContactDTO addContact(Long userId, ContactDTO contactDTO);

    ContactDTO updateContactById(Long contactId, ContactDTO contactDTO);

    List<ContactDTO> getAllContactsByUserId(Long userId);

    void removeContactById(Long contactId);

    ContactDTO getContactById(Long contactId);
}
