package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setData(userServiceImp.findAll());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
