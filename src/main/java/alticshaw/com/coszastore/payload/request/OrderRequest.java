package alticshaw.com.coszastore.payload.request;

import alticshaw.com.coszastore.entity.OrderProductEntity;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
public class OrderRequest {

    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be at least 0.01.")
    private double price;

    @NotBlank(message = "Name must not be blank.")
    private String state;

    @NotNull(message = "Post Code must not be null.")
    @Positive(message = "Post Code must be a positive integer.")
    private String postCode;

    @NotEmpty(message = "Country ID cannot be empty")
    @Size(min = 1, message = "Country Id must have at least 1 element")
    private int country_id;

    private int coupon_id;

    @NotEmpty(message = "User ID cannot be empty")
    @Size(min = 1, message = "username must have at least 1 element")
    private int user_id;

    private List<Integer> productId;
}
