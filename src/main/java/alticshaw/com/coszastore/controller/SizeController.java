package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.SizeResponse;
import alticshaw.com.coszastore.service.imp.SizeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size")
public class SizeController {
    private final SizeServiceImp sizeServiceImp;

    @Autowired
    public SizeController(SizeServiceImp sizeServiceImp) {
        this.sizeServiceImp = sizeServiceImp;
    }

    @PostMapping("")
    public ResponseEntity<?> addSizes(@RequestParam String name) {
        sizeServiceImp.add(name);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Add size successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllSizes() {
        List<SizeResponse> sizeResponseList = sizeServiceImp.getAllSizes();
        BaseResponse response = new BaseResponse();
        response.setMessage("Get list successfully!");
        response.setData(sizeResponseList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editSize(@PathVariable Integer id, @RequestParam String name) {
        sizeServiceImp.edit(id, name);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Edit successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSize(@PathVariable Integer id) {
        sizeServiceImp.delete(id);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Successfully delete!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
