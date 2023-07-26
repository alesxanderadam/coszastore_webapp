package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.*;
import alticshaw.com.coszastore.entity.ids.ProductColorIds;
import alticshaw.com.coszastore.entity.ids.ProductSizeIds;
import alticshaw.com.coszastore.entity.ids.ProductTagIds;
import alticshaw.com.coszastore.exception.CustomException;
import alticshaw.com.coszastore.exception.NotFoundCustomException;
import alticshaw.com.coszastore.exception.NotImageException;
import alticshaw.com.coszastore.exception.ValidationCustomException;
import alticshaw.com.coszastore.mapper.ModelUtilMapper;
import alticshaw.com.coszastore.payload.request.ProductRequest;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.payload.response.TagResponse;
import alticshaw.com.coszastore.repository.*;
import alticshaw.com.coszastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductServiceImp {
    @Value("${path.root.directory}")
    private String pathImage;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;
    private final ProductColorRepository productColorRepository;
    private final ProductSizeRepository productSizeRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            SizeRepository sizeRepository,
            ColorRepository colorRepository,
            TagRepository tagRepository,
            ProductTagRepository productTagRepository,
            ProductColorRepository productColorRepository,
            ProductSizeRepository productSizeRepository,
            FileStorageService fileStorageService
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.sizeRepository = sizeRepository;
        this.colorRepository = colorRepository;
        this.tagRepository = tagRepository;
        this.productTagRepository = productTagRepository;
        this.productColorRepository = productColorRepository;
        this.productSizeRepository = productSizeRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<ProductResponse> getAll() {
        List<ProductEntity> productEntityList = productRepository.findAllProductsCustom();
        List<ProductResponse> productResponses = new ArrayList<>();
        try {
            for (ProductEntity product : productEntityList) {
                ProductResponse productResponse = ModelUtilMapper.map(product, ProductResponse.class);

                productResponse.setCategory_id(product.getCategory().getId());

                if (productResponse.getImage() != null) {
                    productResponse.setImage(pathImage + File.separator + "images" + File.separator + product.getImage());
                } else {
                    productResponse.setImage(null);
                }
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
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundCustomException("Product not found with id : " + id, HttpStatus.NOT_FOUND.value()));
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
            throw new NotFoundCustomException("Product not found with id : " + id, HttpStatus.NOT_FOUND.value());
        }
        try {
            fileStorageService.deleteByName(product.getImage());
            productRepository.delete(product);
            new MessageResponse().success();
        } catch (Exception e) {
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
                    .orElseThrow(() -> new NotFoundCustomException("Category id not found", HttpStatus.NOT_FOUND.value()));
            ProductEntity product = new ProductEntity();
            if (category == null) {
                throw new NotFoundCustomException("Category id not found", HttpStatus.NOT_FOUND.value());
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
                product.setImage(saveNullOrValidImage(request.getImage()));
                product.setListImage(request.getList_image());
                productRepository.save(product);

            } catch (Exception e) {
                throw new CustomException("Error add product");
            }

            if (request.getSize_id() != null && !request.getSize_id().isEmpty()) {
                List<SizeEntity> sizes = sizeRepository.findAllById(request.idStringToSetInteger(request.getSize_id()));
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
                    throw new NotFoundCustomException("Size id not found ", HttpStatus.NOT_FOUND.value());
                }
            }

            if (request.getColor_id() != null && !request.getColor_id().isEmpty()) {
                List<ColorEntity> colors = colorRepository.findAllById(request.idStringToSetInteger(request.getColor_id()));

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
                } else {
                    throw new NotFoundCustomException("Color id not found ", HttpStatus.NOT_FOUND.value());
                }
            }

            if (request.getTag_id() != null && !request.getTag_id().isEmpty()) {
                List<TagEntity> tags = tagRepository.findAllById(request.idStringToSetInteger(request.getTag_id()));

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
                    throw new NotFoundCustomException("Tag id not found ", HttpStatus.NOT_FOUND.value());
                }
            }

            productSizeRepository.saveAll(product.getProductSizes());
            productColorRepository.saveAll(product.getProductColors());
            productTagRepository.saveAll(product.getProductTags());

            ProductEntity productEntity = productRepository.findByProductCustom(product.getId());
            ProductResponse productResponse = ModelUtilMapper.map(productEntity, ProductResponse.class);

            productResponse.setCategory_id(product.getCategory().getId());

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

            if (productResponse.getImage() != null) {
                productResponse.setImage(pathImage + File.separator + "images" + File.separator + product.getImage());
            } else {
                productResponse.setImage(null);
            }
            return productResponse;
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    private ProductResponse updateProduct(ProductRequest request, Integer id) {
        ProductEntity product = productRepository.findByProductCustom(id);
        if (product == null) {
            throw new NotFoundCustomException("Product id not found", HttpStatus.NOT_FOUND.value());
        }
        try {
            CategoryEntity category = categoryRepository.findById(request.getCategory_id())
                    .orElseThrow(() -> new NotFoundCustomException("Category id not found with ID: " + id, HttpStatus.NOT_FOUND.value()));

            if (request.getImage() != null) {
                fileStorageService.deleteByName(product.getImage());
                product.setImage(saveNullOrValidImage(request.getImage()));
            } else {
                product.setImage(product.getImage());
            }

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
                product.setListImage(request.getList_image());

                productRepository.save(product);
            } catch (Exception e) {
                throw new CustomException("Error add product");
            }

            if (request.getSize_id() != null && !request.getSize_id().isEmpty()) {
                List<SizeEntity> sizes = sizeRepository.findAllById(request.idStringToSetInteger(request.getSize_id()));
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
                    throw new NotFoundCustomException("Size id not found with ID: " + request.getSize_id(), HttpStatus.NOT_FOUND.value());

                }
            }

            if (request.getColor_id() != null && !request.getColor_id().isEmpty()) {
                List<ColorEntity> colors = colorRepository.findAllById(request.idStringToSetInteger(request.getColor_id()));

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
                    throw new NotFoundCustomException("Color id not found with Id: " + request.getColor_id(), HttpStatus.NOT_FOUND.value());
                }
            }

            if (request.getTag_id() != null && !request.getTag_id().isEmpty()) {
                List<TagEntity> tags = tagRepository.findAllById(request.idStringToSetInteger(request.getTag_id()));

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
                    throw new NotFoundCustomException("Tag id not found with ID: " + request.getTag_id(), HttpStatus.NOT_FOUND.value());
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

            if (productResponse.getImage() != null) {
                productResponse.setImage(pathImage + File.separator + "images" + File.separator + product.getImage());
            } else {
                productResponse.setImage(null);
            }
            return productResponse;
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    private String saveNullOrValidImage(MultipartFile image) {
        String imageName = null;
        if (!(image == null || image.isEmpty())) {
            if (fileStorageService.isImage(image)) {
                imageName = fileStorageService.save(image);
            } else {
                throw new NotImageException(image.getOriginalFilename() + " is not an image!");
            }
        }
        return imageName;
    }
}
