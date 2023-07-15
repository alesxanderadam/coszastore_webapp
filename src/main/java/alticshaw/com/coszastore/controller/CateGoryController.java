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

@RestController
@RequestMapping("/category")
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
        response.setStatusCode(200);
        response.setMessage("getAllCategory OK");
        response.setData(categoryServiceImp.getAllCategory());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addCategory(@Valid CategoryRequest request, BindingResult result){
        List<FieldError> list=result.getFieldErrors();
        boolean isSuccess=categoryServiceImp.addCategory(request);
        BaseResponse response=new BaseResponse();
        if(isSuccess==false){
            response.setMessage(list.toString());
        }else {
            response.setMessage("Success");
        }
        response.setStatusCode(200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteCategory(@Valid CategoryRequest request){
        BaseResponse response=new BaseResponse();
        boolean isSuccess=categoryServiceImp.deleteCategory(request);
        response.setMessage("success");
        response.setStatusCode(200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateCategory(@Valid CategoryRequest request, BindingResult result ){
        List<FieldError> list=result.getFieldErrors();
        BaseResponse response=new BaseResponse();
        boolean isSuccess=categoryServiceImp.updateCategory(request);

        response.setStatusCode(200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
