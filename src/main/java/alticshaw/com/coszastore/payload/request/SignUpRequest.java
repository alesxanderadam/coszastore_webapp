package alticshaw.com.coszastore.payload.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SignUpRequest extends SignInRequest {
    @NotNull(message = "Username not null")
    @NotEmpty(message = "Username not empty")
    private String username;

    @NotNull(message = "Role not null")
    private int role_id;

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
