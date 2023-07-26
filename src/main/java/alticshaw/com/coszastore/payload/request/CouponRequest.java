package alticshaw.com.coszastore.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequest {
    @NotNull(message = "Coupon code can not be null!")
    @Size(min = 6, message = "Coupon code must have at least 6 characters!")
    private String code;

    @NotNull(message = "Coupon type can not be null!")
    @NotEmpty(message = "Coupon type can not be empty!")
    private String type;

    @NotNull(message = "Coupon promotion can not be null!")
    @Min(value = 0, message = "Promotion value must equal or greater than 0!")
    @Max(value = 100, message = "Promotion value must equal or greater than 100")
    private Double promotion;
}
