package alticshaw.com.coszastore.payload.request;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
@Data
public class ProductRequest {
    @NotBlank(message = "Name must not be blank.")
    private String name;

    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be at least 0.01.")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity cannot be negative.")
    private int quantity;

    private String short_description;
    private String description;
    private String weight;
    private String materials;
    private String dimensions;
    private String image;
    private String list_image;

    @NotNull(message = "Category ID must not be null.")
    @Positive(message = "Category ID must be a positive integer.")
    private Integer category_id;

//    @NotNull(message = "Color ID must not be null.")
//    @Positive(message = "Color ID must be a positive integer.")
//    private Integer color_id;
//
//    @NotNull(message = "Tag ID must not be null.")
//    @Positive(message = "Tag ID must be a positive integer.")
//    private Integer tag_id;
//
//    @NotNull(message = "Size ID must not be null.")
//    @Positive(message = "Size ID must be a positive integer.")
//    private Integer size_id;
}
