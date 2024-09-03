package com.personal.portfolio.Service.impl;

import com.personal.portfolio.Dto.ContactDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Mapper.ContactMapper;
import com.personal.portfolio.Model.Contact;
import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.ContactRepository;
import com.personal.portfolio.Repository.UserRepository;
import com.personal.portfolio.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ContactDTO addContact(Long userId, ContactDTO contactDTO) {
        // Find user by ID or throw exception if not found
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        // Map DTO to entity
        Contact contact = ContactMapper.toEntity(contactDTO);
        contact.setUser(user);  // Set user for the contact

        // Save and return the mapped DTO
        Contact savedContact = contactRepository.saveAndFlush(contact);
        return ContactMapper.toDTO(savedContact);
    }



    @Override
    public ContactDTO updateContactById(Long contactId, ContactDTO contactDTO) {
        // Find contact by ID or throw exception if not found
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "ID", contactId));

        // Update contact fields
        contact.setContactType(contactDTO.getContactType());
        contact.setContactValue(contactDTO.getContactValue());

        // Save updated contact and map to DTO
        Contact updatedContact = contactRepository.saveAndFlush(contact);
        return ContactMapper.toDTO(updatedContact);
    }


    @Override
    public List<ContactDTO> getAllContactsByUserId(Long userId) {
        // Verify if user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "ID", userId);
        }
        Users users = userRepository.findById(userId).get();
        // Fetch contacts by user ID
        List<Contact> contacts = contactRepository.findAllByUser(users);

        // Map contacts to DTOs
        return contacts.stream()
                .map(ContactMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeContactById(Long contactId) {
        // Check if contact exists before deletion
        if (!contactRepository.existsById(contactId)) {
            throw new ResourceNotFoundException("Contact", "ID", contactId);
        }

        // Remove contact
        contactRepository.deleteById(contactId);
    }

    @Override
    public ContactDTO getContactById(Long contactId) {
        // Find contact by ID or throw exception if not found
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "ID", contactId));

        // Map contact to DTO
        return ContactMapper.toDTO(contact);
    }
}
