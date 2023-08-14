package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.ContactEntity;
import alticshaw.com.coszastore.exception.ContactNotFoundException;
import alticshaw.com.coszastore.exception.CustomIllegalArgumentException;
import alticshaw.com.coszastore.exception.CustomValidationException;
import alticshaw.com.coszastore.payload.request.ContactRequest;
import alticshaw.com.coszastore.payload.response.ContactResponse;
import alticshaw.com.coszastore.repository.ContactRepository;
import alticshaw.com.coszastore.service.imp.ContactServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ContactService implements ContactServiceImp {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @Override
    public void add(ContactRequest contactRequest, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            contactRepository.save(new ContactEntity(
                    contactRequest.getEmail(),
                    contactRequest.getMessage()
            ));
        } else {
            throw new CustomValidationException(bindingResult);
        }
    }

    @Override
    public List<ContactResponse> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(contactDtoMapper())
                .limit(100)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        try {
            int contactId = Integer.parseInt(id);
            ContactEntity contact = contactRepository.findById(contactId)
                    .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + id));
            contactRepository.delete(contact);
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Illegal id: " + id);
        }
    }

    private Function<ContactEntity, ContactResponse> contactDtoMapper() {
        return contactEntity -> new ContactResponse(
                contactEntity.getId(),
                contactEntity.getEmail(),
                contactEntity.getMessage()
        );
    }
}
