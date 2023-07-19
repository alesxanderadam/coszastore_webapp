package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user_id={user_id}")
    public ResponseEntity<?> findById(@PathVariable int user_id) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Success!!!!");
        response.setData(userServiceImp.getUserById(user_id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserEntity userEntityUpdate, BindingResult errors) {
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BaseResponse response = new BaseResponse();
        response.setStatusCode(201);
        response.setMessage("Create!!!!");
        response.setData(userServiceImp.addUser(userEntityUpdate));
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id")Integer userId,
                                        @RequestBody UserEntity userEntityUpdate, BindingResult errors) {
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BaseResponse response = new BaseResponse();
        response.setStatusCode(202);
        response.setMessage("Update Sucess!!!!");
        response.setData(userServiceImp.updateUser(userId, userEntityUpdate));

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id")Integer userId,
                                        @RequestBody UserEntity userEntityUpdate, BindingResult errors) {
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Delete Sucess!!!!");
        response.setData(userServiceImp.deleteUser(userId, userEntityUpdate));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
