package alticshaw.com.coszastore.controller;


import alticshaw.com.coszastore.entity.CategoryEntity;
import alticshaw.com.coszastore.payload.request.CategoryRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.CategoryService;
import alticshaw.com.coszastore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CateGoryController {
    @Autowired
    private CategoryServiceImp categoryServiceImp;
    @GetMapping("/clear-cache")
    @CacheEvict(value ="listCategory", allEntries = true)
    public ResponseEntity<?> clearCache(){
        return new ResponseEntity<>("", HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllCategory(){
        BaseResponse response=new BaseResponse();
        response.setData(categoryServiceImp.getAllCategory());
        response.setStatusCode(200);
        response.setMessage("success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryRequest request, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        categoryServiceImp.addCategory(request);
        BaseResponse response=new BaseResponse();
        response.setMessage("success");
        response.setStatusCode(201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteCategory(@RequestBody @Valid CategoryRequest request, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BaseResponse response=new BaseResponse();
        categoryServiceImp.deleteCategory(request);
        response.setMessage("success");
        response.setStatusCode(200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryRequest request, BindingResult result ){
        //List<FieldError> list=result.getFieldErrors();
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BaseResponse response=new BaseResponse();
        categoryServiceImp.updateCategory(request);
        response.setMessage("success");
        response.setStatusCode(200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
