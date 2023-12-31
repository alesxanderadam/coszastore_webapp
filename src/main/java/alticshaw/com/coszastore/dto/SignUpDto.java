package alticshaw.com.coszastore.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignUpDto extends SignInDto{
    @NotNull(message = "Username not null")
    @NotEmpty(message = "Username not empty")
    private String username;

    @NotNull(message = "Role not null")
    @Digits(integer = 10, fraction = 0, message = "Role must be a valid number")
    private Integer role_id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
