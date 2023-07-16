package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.exception.CustomException;
import alticshaw.com.coszastore.payload.response.UserResponse;
import alticshaw.com.coszastore.repository.UserRepository;
import alticshaw.com.coszastore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponse> findAll() {
        List<UserEntity> userList = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();

        for (UserEntity data : userList) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(data.getId());
            userResponse.setUsername(data.getUsername());
            userResponse.setEmail(data.getEmail());
            userResponse.setCreatedTime(data.getCreatedTime());
            userResponse.setUpdatedTime(data.getUpdatedTime());
            userResponse.setRoleId(data.getRole());
            userResponseList.add(userResponse);
        }

        return userResponseList;
    }

    @Override
    public boolean addUser(UserEntity userEntity) {
        boolean isSuccess;
        try {
            userRepository.save(userEntity);
            isSuccess = true;
        }catch (Exception e) {
            throw new CustomException("Error addUser "+e.getMessage());

        }
        return isSuccess;
    }

    @Override
    public boolean updateUser(Integer userId, UserEntity updatedUser) {
        boolean isSuccess;
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));;
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUpdatedTime(updatedUser.getUpdatedTime());
        userRepository.save(existingUser);
        isSuccess = true;

        return isSuccess;
    }

    @Override
    public boolean deleteUser(Integer userId, UserEntity updatedUser) {
        boolean isSuccess;
        UserEntity deleteUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));;
        userRepository.delete(deleteUser);
        isSuccess = true;

        return isSuccess;
    }


}
