package alticshaw.com.coszastore.payload.request;

import alticshaw.com.coszastore.entity.enums.Status;
import lombok.Data;

@Data
public class UserRequest {


    private String username;


    private String password;


    private String email;


    private String address;

    private String avatar;

    private String phone_number;


    private Status status;


    private int role_id;
}
