package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderServiceImp orderServiceImp;

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Success!!!!");
        response.setData(orderServiceImp.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
