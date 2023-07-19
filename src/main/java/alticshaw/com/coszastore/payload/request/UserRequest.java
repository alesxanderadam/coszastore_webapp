package alticshaw.com.coszastore.payload.request;

import alticshaw.com.coszastore.entity.RoleEntity;
import alticshaw.com.coszastore.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    @NotNull
    @NotEmpty
    private int id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotEmpty
    private String email;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phone_number;

    @NotEmpty
    private Status status;

    @NotNull
    @NotEmpty
    private int role;
}
