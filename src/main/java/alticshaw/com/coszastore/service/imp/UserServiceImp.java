package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.request.UserRequest;
import alticshaw.com.coszastore.payload.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserServiceImp {
    List<UserResponse> findAll();
    Optional<UserEntity> getUserById(Integer id);
    boolean addUser(UserEntity userEntity);
    boolean updateUser(Integer userId, UserRequest updatedUser);
    boolean deleteUser(Integer userId,UserEntity updatedUser);
}
