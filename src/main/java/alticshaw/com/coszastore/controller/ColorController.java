package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.ColorResponse;
import alticshaw.com.coszastore.service.imp.ColorServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/color")
public class ColorController {
    private final ColorServiceImp colorServiceImp;

    @Autowired
    public ColorController(ColorServiceImp colorServiceImp) {
        this.colorServiceImp = colorServiceImp;
    }

    @PostMapping("")
    public ResponseEntity<?> addColor(@RequestParam String name) {
        colorServiceImp.add(name);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Add color successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllColors() {
        List<ColorResponse> colorResponseList = colorServiceImp.getAllColors();
        BaseResponse response = new BaseResponse();
        response.setMessage("Get list successfully!");
        response.setData(colorResponseList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editColor(@PathVariable Integer id, @RequestParam String name) {
        colorServiceImp.edit(id, name);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Edit successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteColor(@PathVariable Integer id) {
        colorServiceImp.delete(id);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Successfully delete!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
