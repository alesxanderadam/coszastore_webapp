package alticshaw.com.coszastore.payload.request;

import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "product_color",
            joinColumns = @JoinColumn(name = "product_id"))
    @NotEmpty(message = "Color IDs cannot be empty")
    @Size(min = 1, message = "Color Id must have at least 1 element")
    private List<Integer> color_id;

    @ElementCollection
    @CollectionTable(name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"))
    @NotEmpty(message = "Tag IDs cannot be empty")
    @Size(min = 1, message = "Tag Id must have at least 1 element")
    private List<Integer> tag_id;

    @ElementCollection
    @CollectionTable(name = "product_size",
            joinColumns = @JoinColumn(name = "product_id"))
    @NotEmpty(message = "Size IDs cannot be empty")
    @Size(min = 1, message = "Size Id must have at least 1 element")
    private List<Integer> size_id;
}
