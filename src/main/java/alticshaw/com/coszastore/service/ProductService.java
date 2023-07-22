package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.*;
import alticshaw.com.coszastore.mapper.ModelUtilMapper;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.payload.response.TagResponse;
import alticshaw.com.coszastore.repository.ProductRepository;
import alticshaw.com.coszastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService implements ProductServiceImp {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> getAll() {
        Set<ProductEntity> productEntityList = productRepository.findAllProductsWithSizes();
        List<ProductResponse> productResponses = new ArrayList<>();
        List<String> sizeList = new ArrayList<>();
        List<String> colorList = new ArrayList<>();
        List<TagEntity> tagList = new ArrayList<>();
        Set<TagResponse> tagResponses = new HashSet<>();
        for (ProductEntity product : productEntityList) {
            ProductResponse productResponse = ModelUtilMapper.map(product, ProductResponse.class);
            for (ProductSizeEntity size : product.getProductSizes()) {
                sizeList.add(size.getSize().getName());
            }
            productResponse.setSize(sizeList);

            for (ProductColorEntity color : product.getProductColors()) {
                colorList.add(color.getColor().getName());
            }
            productResponse.setColor(colorList);

            for (ProductTagEntity tag : product.getProductTags()){
                tagList.add(tag.getTag());
            }

            productResponses.add(productResponse);
        }
        return productResponses;
    }
}
