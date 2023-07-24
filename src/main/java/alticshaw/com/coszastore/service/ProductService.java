package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.*;
import alticshaw.com.coszastore.entity.ids.ProductColorIds;
import alticshaw.com.coszastore.entity.ids.ProductSizeIds;
import alticshaw.com.coszastore.entity.ids.ProductTagIds;
import alticshaw.com.coszastore.exception.CustomException;
import alticshaw.com.coszastore.exception.NotFoundCustomException;
import alticshaw.com.coszastore.exception.ValidationCustomException;
import alticshaw.com.coszastore.mapper.ModelUtilMapper;
import alticshaw.com.coszastore.payload.request.ProductRequest;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.payload.response.TagResponse;
import alticshaw.com.coszastore.repository.*;
import alticshaw.com.coszastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductServiceImp {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;
    private final ProductColorRepository productColorRepository;
    private final ProductSizeRepository productSizeRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, SizeRepository sizeRepository,
                          ColorRepository colorRepository,
                          TagRepository tagRepository,
                          ProductTagRepository productTagRepository,
                          ProductColorRepository productColorRepository,
                          ProductSizeRepository productSizeRepository

    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.sizeRepository = sizeRepository;
        this.colorRepository = colorRepository;
        this.tagRepository = tagRepository;
        this.productTagRepository = productTagRepository;
        this.productColorRepository = productColorRepository;
        this.productSizeRepository = productSizeRepository;
    }

    @Override
    public List<ProductResponse> getAll() {
        List<ProductEntity> productEntityList = productRepository.findAllProductsCustom();
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
        } catch (CustomException e) {
            throw new CustomException("Error get list product");
        }
        return productResponses;
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest, BindingResult signUpBindingResult) {
        validateRequest(signUpBindingResult);
        return saveProduct(productRequest);
    }

    @Override
    public ProductResponse getProductById(Integer id) {
        ProductEntity product = productRepository.findByProductCustom(id);

        if (product == null) {
            throw new NotFoundCustomException("Product not found with id : " + id, 404);
        }

        try {
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

            return productResponse;
        } catch (Exception e) {
            throw new CustomException("Get product by id error " + e.getMessage());
        }
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest, Integer id, BindingResult updateBindingResult) {
        validateRequest(updateBindingResult);
        return updateProduct(productRequest, id);
    }

    @Override
    public void deleteProduct(Integer id) {
        ProductEntity product = productRepository.findByProductCustom(id);

        if (product == null) {
            throw new NotFoundCustomException("Product not found with id : " + id, 404);
        }
        try {
            productRepository.delete(product);
            new MessageResponse().success();
        }catch (Exception e){
            throw new CustomException("Delete failed: " + e.getMessage());
        }
    }

    private void validateRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationCustomException(bindingResult);
        }
    }

    private ProductResponse saveProduct(ProductRequest request) {
        try {
            CategoryEntity category = categoryRepository.findById(request.getCategory_id())
                    .orElseThrow(() -> new NotFoundCustomException("Category id not found", 404));
            ProductEntity product = new ProductEntity();
            categoryRepository.findById(request.getCategory_id()).ifPresent(product::setCategory);
            if (categoryRepository.findById(request.getCategory_id()).isEmpty()) {
                throw new NotFoundCustomException("Category id not found", 404);
            }
            try {
                product.setName(request.getName());
                product.setShort_description(request.getShort_description());
                product.setDescription(request.getDescription());
                product.setPrice(request.getPrice());
                product.setCategory(category);
                product.setQuantity(request.getQuantity());
                product.setDimensions(request.getDimensions());
                product.setWeight(request.getWeight());
                product.setMaterials(request.getMaterials());
                product.setImage(request.getImage());
                product.setListImage(request.getList_image());
                productRepository.save(product);
            } catch (Exception e) {
                throw new CustomException("Error add product");
            }

            if (request.getSize_id() != null && !request.getSize_id().isEmpty()) {
                List<SizeEntity> sizes = sizeRepository.findAllById(request.getSize_id());
                if (!sizes.isEmpty()) {
                    Set<ProductSizeEntity> productSizes = product.getProductSizes();
                    if (productSizes == null) {
                        productSizes = new HashSet<>();
                    }
                    productSizes.addAll(sizes.stream().map(size -> {
                        ProductSizeEntity productSizeEntity = new ProductSizeEntity();
                        ProductSizeIds productSizeIds = new ProductSizeIds();

                        productSizeIds.setProductId(product.getId());
                        productSizeIds.setSizeId(size.getId());

                        productSizeEntity.setIds(productSizeIds);
                        productSizeEntity.setSize(size);
                        productSizeEntity.setProduct(product);
                        return productSizeEntity;
                    }).collect(Collectors.toSet()));
                    product.setProductSizes(productSizes);
                } else {
                    throw new NotFoundCustomException("Size id not found ", 404);
                }
            }

            if (request.getColor_id() != null && !request.getColor_id().isEmpty()) {
                List<ColorEntity> colors = colorRepository.findAllById(request.getColor_id());

                if (!colors.isEmpty()) {
                    Set<ProductColorEntity> productColors = product.getProductColors();
                    if (productColors == null) {
                        productColors = new HashSet<>();
                    }
                    productColors.addAll(colors.stream().map(color -> {
                        ProductColorEntity productColorEntity = new ProductColorEntity();
                        ProductColorIds productColorIds = new ProductColorIds();

                        productColorIds.setProductId(product.getId());
                        productColorIds.setColorId(color.getId());
                        productColorEntity.setColor(color);

                        productColorEntity.setIds(productColorIds);
                        productColorEntity.setProduct(product);
                        return productColorEntity;
                    }).collect(Collectors.toSet()));
                    product.setProductColors(productColors);
                } else {
                    throw new NotFoundCustomException("Color id not found ", 404);
                }
            }

            if (request.getTag_id() != null && !request.getTag_id().isEmpty()) {
                List<TagEntity> tags = tagRepository.findAllById(request.getTag_id());

                if (!tags.isEmpty()) {
                    Set<ProductTagEntity> productTags = product.getProductTags();
                    if (productTags == null) {
                        productTags = new HashSet<>();
                    }
                    productTags.addAll(tags.stream().map(tag -> {
                        ProductTagEntity productTagEntity = new ProductTagEntity();
                        ProductTagIds productTagIds = new ProductTagIds();

                        productTagIds.setProductId(product.getId());
                        productTagIds.setTagId(tag.getId());

                        productTagEntity.setIds(productTagIds);
                        productTagEntity.setTag(tag);
                        productTagEntity.setProduct(product);
                        return productTagEntity;
                    }).collect(Collectors.toSet()));
                    product.setProductTags(productTags);
                } else {
                    throw new NotFoundCustomException("Tag id not found ", 404);
                }
            }

            productSizeRepository.saveAll(product.getProductSizes());
            productColorRepository.saveAll(product.getProductColors());
            productTagRepository.saveAll(product.getProductTags());

            ProductEntity productEntity = productRepository.findByProductCustom(product.getId());
            ProductResponse productResponse = ModelUtilMapper.map(productEntity, ProductResponse.class);

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

            return productResponse;
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    private ProductResponse updateProduct(ProductRequest request, Integer id) {
        try {
            ProductEntity product = productRepository.findByProductCustom(id);
            CategoryEntity category = categoryRepository.findById(request.getCategory_id())
                    .orElseThrow(() -> new NotFoundCustomException("Category id not found with ID: " + id, 404));
            try {
                categoryRepository.findById(request.getCategory_id()).ifPresent(product::setCategory);
                product.setName(request.getName());
                product.setShort_description(request.getShort_description());
                product.setDescription(request.getDescription());
                product.setPrice(request.getPrice());
                product.setCategory(category);
                product.setQuantity(request.getQuantity());
                product.setDimensions(request.getDimensions());
                product.setWeight(request.getWeight());
                product.setMaterials(request.getMaterials());
                product.setImage(request.getImage());
                product.setListImage(request.getList_image());

                productRepository.save(product);
            } catch (Exception e) {
                throw new CustomException("Error add product");
            }

            if (request.getSize_id() != null && !request.getSize_id().isEmpty()) {
                List<SizeEntity> sizes = sizeRepository.findAllById(request.getSize_id());
                if (!sizes.isEmpty()) {
                    Set<ProductSizeEntity> productSizes = sizes.stream().map(size -> {
                        ProductSizeEntity productSizeEntity = new ProductSizeEntity();
                        ProductSizeIds productSizeIds = new ProductSizeIds();

                        productSizeIds.setProductId(product.getId());
                        productSizeIds.setSizeId(size.getId());

                        productSizeEntity.setIds(productSizeIds);
                        productSizeEntity.setSize(size);
                        productSizeEntity.setProduct(product);
                        return productSizeEntity;
                    }).collect(Collectors.toSet());

                    product.setProductSizes(productSizes);
                } else {
                    throw new NotFoundCustomException("Size id not found with ID: " + request.getSize_id(), 404);

                }
            }

            if (request.getColor_id() != null && !request.getColor_id().isEmpty()) {
                List<ColorEntity> colors = colorRepository.findAllById(request.getColor_id());

                if (!colors.isEmpty()) {
                    Set<ProductColorEntity> productColors = colors.stream().map(color -> {
                        ProductColorEntity productColorEntity = new ProductColorEntity();
                        ProductColorIds productColorIds = new ProductColorIds();

                        productColorIds.setProductId(product.getId());
                        productColorIds.setColorId(color.getId());
                        productColorEntity.setColor(color);

                        productColorEntity.setIds(productColorIds);
                        productColorEntity.setProduct(product);
                        return productColorEntity;
                    }).collect(Collectors.toSet());

                    product.setProductColors(productColors);
//                    if (productColors == null) {
//                        productColors = new HashSet<>();
//                    }
//                    productColors.addAll(colors.stream().map(color -> {
//                        ProductColorEntity productColorEntity = new ProductColorEntity();
//                        ProductColorIds productColorIds = new ProductColorIds();
//
//                        productColorIds.setProductId(product.getId());
//                        productColorIds.setColorId(color.getId());
//                        productColorEntity.setColor(color);
//
//                        productColorEntity.setIds(productColorIds);
//                        productColorEntity.setProduct(product);
//                        return productColorEntity;
//                    }).collect(Collectors.toSet()));
                } else {
                    throw new NotFoundCustomException("Color id not found with Id: " + request.getColor_id(), 404);
                }
            }

            if (request.getTag_id() != null && !request.getTag_id().isEmpty()) {
                List<TagEntity> tags = tagRepository.findAllById(request.getTag_id());

                if (!tags.isEmpty()) {
                    Set<ProductTagEntity> productTags = tags.stream().map(tag -> {
                        ProductTagEntity productTagEntity = new ProductTagEntity();
                        ProductTagIds productTagIds = new ProductTagIds();

                        productTagIds.setProductId(product.getId());
                        productTagIds.setTagId(tag.getId());

                        productTagEntity.setIds(productTagIds);
                        productTagEntity.setTag(tag);
                        productTagEntity.setProduct(product);
                        return productTagEntity;
                    }).collect(Collectors.toSet());

                    product.setProductTags(productTags);
                } else {
                    throw new NotFoundCustomException("Tag id not found with ID: " + request.getTag_id(), 404);
                }
            }

            productSizeRepository.saveAll(product.getProductSizes());
            productColorRepository.saveAll(product.getProductColors());
            productTagRepository.saveAll(product.getProductTags());

            ProductEntity productEntity = productRepository.findByProductCustom(product.getId());
            ProductResponse productResponse = ModelUtilMapper.map(productEntity, ProductResponse.class);

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

            return productResponse;
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }
}
