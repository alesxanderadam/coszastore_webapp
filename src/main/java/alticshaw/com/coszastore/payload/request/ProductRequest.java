package alticshaw.com.coszastore.payload.request;

import alticshaw.com.coszastore.dto.ProductColorDto;
import alticshaw.com.coszastore.dto.ProductSizeDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@ApiModel(description = "Product request")
public class ProductRequest {
    @ApiModelProperty(value = "Product image", required = true, position = 8)
    private MultipartFile image;

    @ApiModelProperty(value = "Product name", required = true, example = "Example Product", position = 1)
    @NotBlank(message = "Name must not be blank.")
    private String name;

    @ApiModelProperty(value = "Product short description", example = "Short description of the product", position = 2)
    private String short_description;

    @ApiModelProperty(value = "Product description", example = "Detailed description of the product", position = 3)
    private String description;

    @ApiModelProperty(value = "Product weight", example = "0.5 kg", position = 4)
    private String weight;

    @ApiModelProperty(value = "Product materials", example = "Cotton, Polyester", position = 5)
    private String materials;

    @ApiModelProperty(value = "Product dimensions", example = "10 x 5 x 2 cm", position = 6)
    private String dimensions;

    @ApiModelProperty(value = "Product import price", required = true, example = "10.99", position = 7)
    @DecimalMin(value = "10.99", message = "Price must be at least 0.01.")
    private BigDecimal import_price;

    @ApiModelProperty(value = "Product price", required = true, example = "19.99", position = 8)
    @DecimalMin(value = "12.99", message = "Price must be at least 0.01.")
    private BigDecimal price;

    @ApiModelProperty(value = "Product quantity", required = true, example = "100", position = 9)
    @Min(value = 1, message = "Quantity cannot be negative.")
    private int quantity;

    @ApiModelProperty(value = "Is new product ?", example = "1")
    private int is_new_product;

    @ApiModelProperty(value = "Product is best seller ?", example = "0")
    private int is_best_selling;

    @ApiModelProperty(value = "Category ID", required = true, example = "1", position = 10)
    @NotNull(message = "Category ID must not be null.")
    private Integer category_id;

    @ApiModelProperty(value = "Tag IDs", required = true, example = "[1, 2, 3]", position = 11)
    @NotEmpty(message = "Tag must not be empty.")
    private Set<Integer> tag_id;

    private ProductColorDto color;

    private ProductSizeDto size;

}
