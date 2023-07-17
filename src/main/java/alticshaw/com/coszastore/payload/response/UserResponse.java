package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.entity.UserEntity;

public class UserResponse {
    private int id;

    private String username;

    private String email;

    private String role_name;

    public UserResponse(int id, String username, String email, String role_name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role_name = role_name;
    }

    public UserResponse MapUserEntityToUserResponse(UserEntity user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole().getName());
    }

    public UserResponse(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
