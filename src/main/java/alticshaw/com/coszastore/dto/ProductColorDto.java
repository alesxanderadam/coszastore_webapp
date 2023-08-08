package alticshaw.com.coszastore.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductColorDto {

    @ApiModelProperty(value = "Product color", example = "[1,2,3]",position = 13)
    @NotBlank(message = "Color id must not be blank.")
    @Positive(message = "Color id is not null or empty")
    @Size(min = 1, message = "Color Id must have at least 1 element")
    Set<Integer> color_id;

    @ApiModelProperty(value = "Quantity color", example = "22" ,position = 14)
    @Size(min = 1 , message = "Color Id must have at least 1 element")
    int quantity;
}
