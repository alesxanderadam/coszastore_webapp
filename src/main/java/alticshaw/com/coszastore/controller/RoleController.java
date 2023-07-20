package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.entity.RoleEntity;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.RoleServiceImp;
import alticshaw.com.coszastore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleServiceImp roleServiceImp;

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Success!!!!");
        response.setData(roleServiceImp.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRole(@RequestBody RoleEntity roleEntityUpdate, BindingResult errors) {
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BaseResponse response = new BaseResponse();
        response.setStatusCode(201);
        response.setMessage("Create!!!!");
        response.setData(roleServiceImp.addRole(roleEntityUpdate));
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRole(@PathVariable("id")Integer userId,
                                        @RequestBody RoleEntity roleEntityUpdate, BindingResult errors) {
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BaseResponse response = new BaseResponse();
        response.setStatusCode(202);
        response.setMessage("Update Sucess!!!!");
        response.setData(roleServiceImp.updateRole(userId, roleEntityUpdate));

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id")Integer userId,
                                        @RequestBody RoleEntity roleEntityUpdate, BindingResult errors) {
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Delete Sucess!!!!");
        response.setData(roleServiceImp.deleteRole(userId, roleEntityUpdate));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
