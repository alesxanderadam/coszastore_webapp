package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.UserEntity;
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

            userResponseList.add(userResponse);
        }

        return userResponseList;
    }
}
