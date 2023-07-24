package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.request.ProductRequest;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ProductServiceImp {
    List<ProductResponse> getAll();
    ProductResponse addProduct(ProductRequest productRequest, BindingResult signUpBindingResult);
    ProductResponse getProductById(Integer id);
    ProductResponse updateProduct(ProductRequest productRequest, Integer id, BindingResult signUpBindingResult);
    void deleteProduct(Integer id);
}
