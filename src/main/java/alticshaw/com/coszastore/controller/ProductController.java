package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final BaseResponse baseResponse;
    private final MessageResponse messageResponse;

    @Autowired
    public ProductController(
            BaseResponse baseResponse,
            MessageResponse messageResponse
    ){
        this.baseResponse = baseResponse;
        this.messageResponse = messageResponse;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body("Product");
    }
}
