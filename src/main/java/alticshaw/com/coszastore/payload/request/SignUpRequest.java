package alticshaw.com.coszastore.payload.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignUpRequest extends SignInRequest {
    @NotNull(message = "Username not null")
    @NotEmpty(message = "Username not empty")
    private String username;

    @NotNull(message = "Role not null")
    @Digits(integer = 10, fraction = 0, message = "Role must be a valid number")
    private Integer role_id;
}
