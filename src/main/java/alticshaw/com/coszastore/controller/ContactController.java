package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.request.ContactRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.ContactResponse;
import alticshaw.com.coszastore.service.imp.ContactServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
    private final ContactServiceImp contactServiceImp;

    @Autowired
    public ContactController(ContactServiceImp contactServiceImp) {
        this.contactServiceImp = contactServiceImp;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitContact(
            @RequestBody @Valid ContactRequest contactRequest,
            BindingResult bindingResult
    ) {
        contactServiceImp.add(contactRequest, bindingResult);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Submit contact successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllContacts() {
        List<ContactResponse> responseList = contactServiceImp.getAllContacts();
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Get list contacts successfully!");
        response.setData(responseList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        contactServiceImp.delete(id);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Delete successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
