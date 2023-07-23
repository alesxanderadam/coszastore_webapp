package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.TagServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagServiceImp tagServiceImp;

    @Autowired
    public TagController(TagServiceImp tagServiceImp) {
        this.tagServiceImp = tagServiceImp;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTag(@RequestParam String name) {
        boolean isSuccess = tagServiceImp.add(name);
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
        response.setData(tagServiceImp.getAllResponseTags());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        boolean isSuccess = tagServiceImp.delete(id);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Delete tag successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable String id, @RequestParam String name) {
        boolean isSuccess = tagServiceImp.edit(id, name);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Edit successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
