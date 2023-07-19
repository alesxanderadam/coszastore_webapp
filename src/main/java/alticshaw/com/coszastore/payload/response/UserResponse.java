package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.entity.enums.Status;

public class UserResponse {
    private int id;

    private String username;

    private String email;

    private String address;

    private String phone_number;

    private String avatar;

    private Status status;

    private String role_name;

    public UserResponse(int id, String username, String email, String address, String phone_number, String avatar, Status status, String role_name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
        this.avatar = avatar;
        this.status = status;
        this.role_name = role_name;
    }

    public UserResponse MapUserEntityToUserResponse(UserEntity user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAddress(), user.getPhone_number(), user.getAvatar(), user.getStatus(), user.getRole().getName());
    }

    public UserResponse() {
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
