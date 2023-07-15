package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.request.UserRequest;
import alticshaw.com.coszastore.payload.response.UserResponse;

import java.util.List;

public interface UserServiceImp {
    List<UserResponse> findAll();

    boolean addUser(UserEntity userEntity);

}
