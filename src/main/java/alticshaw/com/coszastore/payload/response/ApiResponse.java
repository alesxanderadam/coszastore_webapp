package alticshaw.com.coszastore.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@ApiModel(description = "API response wrapper")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    @ApiModelProperty(value = "Response status code", example = "200")
    private int statusCode;

    @ApiModelProperty(value = "Response message", example = "Success")
    private String message;

    @ApiModelProperty(value = "Response data")
    private T data;

}