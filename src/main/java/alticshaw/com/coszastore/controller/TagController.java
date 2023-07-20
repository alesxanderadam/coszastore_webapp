package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.TagServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagServiceImp tagServiceImp;

    @Autowired
    public TagController(TagServiceImp tagServiceImp) {
        this.tagServiceImp = tagServiceImp;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTag(@RequestParam String tagName) {
        boolean isSuccess = tagServiceImp.add(tagName);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Save tag successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllTags() {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Get list all tags successfully!");
        response.setData(tagServiceImp.getAllTags());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
