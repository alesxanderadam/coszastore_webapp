package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final BaseResponse baseResponse;
    private final MessageResponse messageResponse;
    private final ProductServiceImp productServiceImp;

    @Autowired
    public ProductController(
            BaseResponse baseResponse,
            MessageResponse messageResponse,
            ProductServiceImp productServiceImp
    ){
        this.baseResponse = baseResponse;
        this.messageResponse = messageResponse;
        this.productServiceImp = productServiceImp;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<ProductResponse> productResponses = productServiceImp.getAll();
        baseResponse.setMessage(messageResponse.success());
        baseResponse.setStatusCode(200);
        baseResponse.setData(productResponses);
        return ResponseEntity.ok().body(baseResponse);
    }
}
