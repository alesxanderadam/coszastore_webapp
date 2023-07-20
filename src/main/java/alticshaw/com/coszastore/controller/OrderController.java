package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.entity.OrderEntity;
import alticshaw.com.coszastore.payload.request.OrderRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.OrderResponse;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderServiceImp orderServiceImp;


    @GetMapping
    public ResponseEntity<?> findAll() {
        List<OrderResponse> orderResponse = orderServiceImp.findAll();
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Success!!!!");
        response.setData(orderResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderRequest orderRequest, BindingResult errors) {
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Save order successfully!");
        response.setData(orderServiceImp.addOrder(orderRequest));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        boolean isSuccess = orderServiceImp.delete(id);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Delete successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
