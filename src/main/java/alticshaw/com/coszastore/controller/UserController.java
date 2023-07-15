package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.request.UserRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Success!!!!");
        response.setData(userServiceImp.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserEntity userEntity, BindingResult errors) {
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BaseResponse response = new BaseResponse();
        response.setStatusCode(201);
        response.setMessage("Create!!!!");
        response.setData(userServiceImp.addUser(userEntity));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
