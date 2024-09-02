package com.personal.portfolio.Controller;


import com.personal.portfolio.Dto.ContactDTO;
import com.personal.portfolio.Exception.ResourceNotFoundException;
import com.personal.portfolio.Response.BaseResponse;
import com.personal.portfolio.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("api/contact")
public class ContactController extends BaseController{


    @Autowired
    ContactService contactService;

    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    // Add a new contact
    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponse<ContactDTO>> addContact(@PathVariable Long userId, @RequestBody ContactDTO contactDTO, Authentication authentication) {
        try {
            ContactDTO response = contactService.addContact(userId, contactDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.CREATED).body(response),
                    authentication,
                    "Contact added successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to add contact: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Contact Add API failed"
            );
        }
    }

    // Update an existing contact
    @PutMapping("/{contactId}")
    public ResponseEntity<BaseResponse<ContactDTO>> updateContactById(@PathVariable Long contactId, @RequestBody ContactDTO contactDTO, Authentication authentication) {
        try {
            ContactDTO response = contactService.updateContactById(contactId, contactDTO);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.ACCEPTED).body(response),
                    authentication,
                    "Contact updated successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("Contact not found for update: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to update contact: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Contact Update API failed"
            );
        }
    }

    // Get all contacts by user ID
    @GetMapping("/all/{userId}")
    public ResponseEntity<BaseResponse<List<ContactDTO>>> getAllContactsByUserId(@PathVariable Long userId, Authentication authentication) {
        try {
            List<ContactDTO> contactList = contactService.getAllContactsByUserId(userId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(contactList),
                    authentication,
                    "Fetched all contacts successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to fetch contacts: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Contact Fetch API failed"
            );
        }
    }

    // Remove a contact by ID
    @DeleteMapping("/{contactId}")
    public ResponseEntity<BaseResponse<Void>> removeContactById(@PathVariable Long contactId, Authentication authentication) {
        try {
            contactService.removeContactById(contactId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(null),
                    authentication,
                    "Contact removed successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("Contact not found for removal: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to remove contact: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Contact Remove API failed"
            );
        }
    }

    // Get a contact by contact ID
    @GetMapping("/{contactId}")
    public ResponseEntity<BaseResponse<ContactDTO>> getContactById(@PathVariable Long contactId, Authentication authentication) {
        try {
            ContactDTO contactDTO = contactService.getContactById(contactId);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(contactDTO),
                    authentication,
                    "Fetched contact successfully"
            );
        } catch (ResourceNotFoundException e) {
            logger.error("Contact not found: {}", e.getMessage());
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null),
                    authentication,
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Failed to fetch contact: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Contact Fetch API failed"
            );
        }
    }

}
