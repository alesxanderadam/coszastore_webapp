package alticshaw.com.coszastore.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizeDto {
    @ApiModelProperty(value = "Product size", example = "[1,2]", position = 14)
    @NotBlank(message = "Color id must not be blank.")
    @Positive(message = "Color id is not null or empty")
    Set<Integer> size_id;

    @ApiModelProperty(value = "Product size", example = "12", position = 15)
    @Size(min = 1, message = "Color Id must have at least 1 element")
    int quantity;
}
