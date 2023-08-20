package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.request.ContactRequest;
import alticshaw.com.coszastore.payload.response.ContactResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ContactServiceImp {
    void add(ContactRequest contactRequest, BindingResult bindingResult);
    List<ContactResponse> getAllContacts();
    void delete(String id);
}
