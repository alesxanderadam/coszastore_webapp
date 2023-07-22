package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.*;
import alticshaw.com.coszastore.exception.CustomException;
import alticshaw.com.coszastore.exception.NotFoundCustomException;
import alticshaw.com.coszastore.exception.ValidationCustomException;
import alticshaw.com.coszastore.mapper.ModelUtilMapper;
import alticshaw.com.coszastore.payload.request.ProductRequest;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.payload.response.TagResponse;
import alticshaw.com.coszastore.repository.CategoryRepository;
import alticshaw.com.coszastore.repository.ProductRepository;
import alticshaw.com.coszastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductServiceImp {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductResponse> getAll() {
        List<ProductEntity> productEntityList = productRepository.findAllProductsWithSizes();
        List<ProductResponse> productResponses = new ArrayList<>();
        try {
            for (ProductEntity product : productEntityList) {
                ProductResponse productResponse = ModelUtilMapper.map(product, ProductResponse.class);

                List<String> sizeList = product.getProductSizes().stream()
                        .map(sizeEntity -> sizeEntity.getSize().getName())
                        .collect(Collectors.toList());
                productResponse.setSize(sizeList);

                List<String> colorList = product.getProductColors().stream()
                        .map(colorEntity -> colorEntity.getColor().getName())
                        .collect(Collectors.toList());
                productResponse.setColor(colorList);

                List<TagResponse> tagResponses = product.getProductTags().stream()
                        .map(tagEntity -> {
                            TagResponse tagResponse = ModelUtilMapper.map(tagEntity, TagResponse.class);
                            tagResponse.setId(tagEntity.getTag().getId());
                            tagResponse.setName(tagEntity.getTag().getName());
                            return tagResponse;
                        })
                        .collect(Collectors.toList());
                productResponse.setTag(tagResponses);

                productResponses.add(productResponse);
            }
        }catch (CustomException e){
            throw new CustomException("Error get list product");
        }
        return productResponses;
    }

    @Override
    public ProductEntity add(ProductRequest productRequest,BindingResult signUpBindingResult) {
        validateRequest(signUpBindingResult);
        return saveProduct(productRequest);
    }

    private void validateRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationCustomException(bindingResult);
        }
    }

    private ProductEntity saveProduct(ProductRequest request) {
        ProductEntity product = new ProductEntity();
        try{
            CategoryEntity category = categoryRepository.getReferenceById(request.getCategory_id());
            if (category == null) {
                throw new NotFoundCustomException("Category id not found", 404);
            }
            product.setName(request.getName());
            product.setCategory(category);
            product.setShort_description(request.getShort_description());
            product.setDescription(request.getName());
            product.setPrice(request.getPrice());
            product.setQuantity(request.getQuantity());
            product.setDimensions(request.getDimensions());
            product.setWeight(request.getWeight());
            product.setMaterials(request.getMaterials());
            product.setImage(request.getImage());
            product.setListImage(request.getList_image());
        }catch (CustomException e){
            throw new CustomException(e.getMessage());
        }
        return productRepository.save(product);
    }
}
