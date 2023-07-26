package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.request.ProductRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    ) {
        this.baseResponse = baseResponse;
        this.messageResponse = messageResponse;
        this.productServiceImp = productServiceImp;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ProductResponse> productResponses = productServiceImp.getAll();
        baseResponse.setMessage(messageResponse.success());
        baseResponse.setStatusCode(200);
        baseResponse.setData(productResponses);
        return ResponseEntity.ok().body(baseResponse);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<?> getById(@PathVariable("product_id") Integer product_id) {
        ProductResponse productResponses = productServiceImp.getProductById(product_id);
        baseResponse.setMessage(messageResponse.success());
        baseResponse.setStatusCode(200);
        baseResponse.setData(productResponses);
        return ResponseEntity.ok().body(baseResponse);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@ModelAttribute @RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult) {
        ProductResponse product = productServiceImp.addProduct(productRequest, bindingResult);

        baseResponse.setMessage(messageResponse.success());
        baseResponse.setStatusCode(200);
        baseResponse.setData(product);
        return ResponseEntity.ok().body(baseResponse);
    }

    @PutMapping(value = "/{product_id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@ModelAttribute @RequestBody @Valid ProductRequest productRequest, @PathVariable("product_id") Integer product_id, BindingResult bindingResult) {
        ProductResponse product = productServiceImp.updateProduct(productRequest, product_id, bindingResult);

        baseResponse.setMessage(messageResponse.success());
        baseResponse.setStatusCode(200);
        baseResponse.setData(product);
        return ResponseEntity.ok().body(baseResponse);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<?> delete(@PathVariable("product_id") Integer product_id) {
        productServiceImp.deleteProduct(product_id);

        baseResponse.setMessage(messageResponse.success());
        baseResponse.setStatusCode(200);
        return ResponseEntity.ok().body(baseResponse);
    }
}
