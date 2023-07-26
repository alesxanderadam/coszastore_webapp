package alticshaw.com.coszastore.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private String list_image;

    private MultipartFile image;

    @NotNull(message = "Category ID must not be null.")
    @Positive(message = "Category ID must be a positive integer.")
    private Integer category_id;

    @NotNull(message = "Color list not null!")
    @NotEmpty(message = "Color list not empty!")
    @Pattern(regexp = "^(?!.*\\b0\\b)(?!.*\\b(\\d+)\\b.*\\b\\1\\b)\\[(?:\\d+(?:,\\d+)*)?]$", message = "Invalid tag list")
    private String color_id;

    @NotNull(message = "Tag list not null!")
    @NotEmpty(message = "Tag list not empty!")
    @Pattern(regexp = "^(?!.*\\b0\\b)(?!.*\\b(\\d+)\\b.*\\b\\1\\b)\\[(?:\\d+(?:,\\d+)*)?]$", message = "Invalid tag list")
    private String tag_id;

    @NotNull(message = "Size list not null!")
    @NotEmpty(message = "Size list not empty!")
    @Pattern(regexp = "^(?!.*\\b0\\b)(?!.*\\b(\\d+)\\b.*\\b\\1\\b)\\[(?:\\d+(?:,\\d+)*)?]$", message = "Invalid tag list")
    private String size_id;


    public Set<Integer> idStringToSetInteger(String id) {
        return Arrays.stream(id.replaceAll("[\\[\\]\\s]", "").split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }
}
