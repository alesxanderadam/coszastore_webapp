package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.dto.RoleDto;
import alticshaw.com.coszastore.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int id;

    private String username;

    private String email;

    private String address;

    private String phone_number;

    private String avatar;

    private Status status;

    private RoleDto role;
}
