package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.request.ProductRequest;
import alticshaw.com.coszastore.payload.request.ProductUploadRequest;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.payload.response.ProductUploadResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ProductServiceImp {
    List<ProductResponse> getAll();
    ProductResponse addProduct(ProductRequest productRequest, BindingResult signUpBindingResult);
    ProductResponse getProductById(Integer id);
    ProductResponse updateProduct(ProductRequest productRequest, Integer id, BindingResult signUpBindingResult);
    ProductUploadResponse uploadImages(ProductUploadRequest productUploadRequest, Integer id);
    void deleteProduct(Integer id);
}
