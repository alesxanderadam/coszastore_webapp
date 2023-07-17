package alticshaw.com.coszastore.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignInDto {
    @NotNull(message = "Password not null")
    @NotEmpty(message = "Password not empty")
    @Length(min = 6)
    private String password;

    @NotNull(message = "Email not null")
    @NotEmpty(message = "Email not empty")
    @Email(message = "Email invalid format")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email invalid format")
    private String email;
}
