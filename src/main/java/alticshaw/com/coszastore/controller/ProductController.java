package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.request.ProductRequest;
import alticshaw.com.coszastore.payload.request.ProductUploadRequest;
import alticshaw.com.coszastore.payload.response.*;
import alticshaw.com.coszastore.payload.response.ApiResponse;
import alticshaw.com.coszastore.service.imp.ProductServiceImp;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@Api(tags = "Product Controller", description = "APIs related to products")
public class ProductController {
    private final MessageResponse messageResponse;
    private final ProductServiceImp productServiceImp;

    @Autowired
    public ProductController(
            MessageResponse messageResponse,
            ProductServiceImp productServiceImp
    ) {
        this.messageResponse = messageResponse;
        this.productServiceImp = productServiceImp;
    }

    @GetMapping
    @ApiOperation(value = "Get all products", notes = "Retrieve a list of all products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAll() {
        List<ProductResponse> productResponses = productServiceImp.getAll();
        ApiResponse<List<ProductResponse>> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setMessage("Success");
        response.setData(productResponses);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{product_id}")
    @ApiOperation(value = "Get a product by ID", notes = "Retrieve a product by its unique ID")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(
            @ApiParam(value = "Product ID", required = true)
            @PathVariable("product_id") Integer product_id) {
        ProductResponse productResponses = productServiceImp.getProductById(product_id);
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        response.setMessage(messageResponse.success());
        response.setStatusCode(200);
        response.setData(productResponses);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping()
    @ApiOperation(value = "Create a new product", notes = "Requires product information")
    public ResponseEntity<ApiResponse<ProductResponse>> add(
            @ApiParam(name = "Product add" ,value = "Product information", required = true) @RequestBody @Valid ProductRequest productRequest,
            BindingResult bindingResult
    ) {
        ProductResponse product = productServiceImp.addProduct(productRequest, bindingResult);
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        response.setMessage(messageResponse.success());
        response.setStatusCode(200);
        response.setData(product);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/uploadImage/{product_id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "Upload product images", notes = "Upload images for a specific product")
    public ResponseEntity<ApiResponse<ProductUploadResponse>> uploadImage(
            @ApiParam(value = "Product ID", required = true) @PathVariable("product_id") Integer product_id,
            @ModelAttribute @Validated ProductUploadRequest request
    ) {
        ProductUploadResponse product = productServiceImp.uploadImages(request, product_id);
        ApiResponse<ProductUploadResponse> response = new ApiResponse<>();
        response.setMessage(messageResponse.success());
        response.setStatusCode(200);
        response.setData(product);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/{product_id}")
    @ApiOperation(value = "Update an existing product", notes = "Update an existing product based on its ID")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @ApiParam(value = "Product informat1ion", required = true) @RequestBody @Valid ProductRequest productRequest,
            @ApiParam(value = "Product ID", required = true) @PathVariable("product_id") Integer product_id,
            BindingResult bindingResult
    ) {
        ProductResponse product = productServiceImp.updateProduct(productRequest, product_id, bindingResult);
        ApiResponse<ProductResponse> response = new ApiResponse<>();

        response.setMessage(messageResponse.success());
        response.setStatusCode(200);
        response.setData(product);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{product_id}")
    @ApiOperation(value = "Delete a product", notes = "Delete a product by its ID")
    public ResponseEntity<ApiResponse<String>> delete(
            @ApiParam(value = "Product ID", required = true) @PathVariable("product_id") Integer product_id) {
        productServiceImp.deleteProduct(product_id);
        ApiResponse<String> response = new ApiResponse<>();

        response.setMessage(messageResponse.success());
        response.setStatusCode(200);
        return ResponseEntity.ok().body(response);
    }
}
