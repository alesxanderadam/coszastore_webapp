package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.entity.ProductEntity;
import alticshaw.com.coszastore.payload.request.ProductRequest;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ProductServiceImp {
    List<ProductResponse> getAll();
    ProductEntity add(ProductRequest productRequest, BindingResult signUpBindingResult);
}