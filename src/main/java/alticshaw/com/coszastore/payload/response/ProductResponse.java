package alticshaw.com.coszastore.payload.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@ApiModel(description = "Product details")
public class ProductResponse {
    @ApiModelProperty(value = "Product ID", example = "1")
    private int id;

    @ApiModelProperty(value = "Product name", example = "Example Product")
    private String name;

    @ApiModelProperty(value = "Product price", example = "19.99")
    private BigDecimal price;

    @ApiModelProperty(value = "Product quantity", example = "100")
    private int quantity;

    @ApiModelProperty(value = "Product short description", example = "Short description of the product")
    private String short_description;

    @ApiModelProperty(value = "Product description", example = "Detailed description of the product")
    private String description;

    @ApiModelProperty(value = "Product weight", example = "0.5 kg")
    private String weight;

    @ApiModelProperty(value = "Product materials", example = "Cotton, Polyester")
    private String materials;

    @ApiModelProperty(value = "Product dimensions", example = "10 x 5 x 2 cm")
    private String dimensions;

    @ApiModelProperty(value = "URL of the product image")
    private String image;

    @ApiModelProperty(value = "URL of the product list image")
    private String listImage;

    @ApiModelProperty(value = "Category ID", example = "2")
    private int category_id;

    @ApiModelProperty(value = "Category")
    private CategoryResponse category;

    @ApiModelProperty(value = "List of tags associated with the product")
    private List<TagResponse> tag;

    @ApiModelProperty(value = "List of available colors for the product", example = "[\"Red\", \"Blue\", \"Green\"]")
    private List<String> color;

    @ApiModelProperty(value = "List of available sizes for the product", example = "[\"S\", \"M\", \"L\"]")
    private List<String> size;
}
