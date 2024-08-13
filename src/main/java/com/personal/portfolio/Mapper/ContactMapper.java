package com.personal.portfolio.Mapper;


import com.personal.portfolio.Dto.ContactDTO;
import com.personal.portfolio.Model.Contact;

public class ContactMapper {

    public static ContactDTO toDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setContactId(contact.getContactId());
        dto.setContactType(contact.getContactType());
        dto.setContactValue(contact.getContactValue());
        return dto;
    }

    public static Contact toEntity(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setContactId(dto.getContactId());
        contact.setContactType(dto.getContactType());
        contact.setContactValue(dto.getContactValue());
        return contact;
    }
}
