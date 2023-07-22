package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.entity.CategoryEntity;
import alticshaw.com.coszastore.entity.ProductEntity;
import alticshaw.com.coszastore.entity.RoleEntity;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.exception.ConflictCustomException;
import alticshaw.com.coszastore.exception.NotFoundCustomException;
import alticshaw.com.coszastore.exception.ValidationCustomException;
import alticshaw.com.coszastore.mapper.ModelUtilMapper;
import alticshaw.com.coszastore.payload.request.ProductRequest;
import alticshaw.com.coszastore.payload.request.SignUpRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.payload.response.UserResponse;
import alticshaw.com.coszastore.repository.CategoryRepository;
import alticshaw.com.coszastore.repository.ProductRepository;
import alticshaw.com.coszastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<?> add(@Valid ProductRequest productRequest, BindingResult bindingResult) {
        ProductEntity product = productServiceImp.add(productRequest, bindingResult);
        ProductResponse productResponse = ModelUtilMapper.map(product, ProductResponse.class);

        baseResponse.setMessage(messageResponse.success());
        baseResponse.setStatusCode(200);
        baseResponse.setData(productResponse);
        return ResponseEntity.ok().body(baseResponse);
    }
}
