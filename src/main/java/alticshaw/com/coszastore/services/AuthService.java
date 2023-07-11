package alticshaw.com.coszastore.services;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.request.SignUpRequest;
import alticshaw.com.coszastore.repository.UserRepository;
import alticshaw.com.coszastore.services.imp.AuthServiceImp;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class AuthService implements AuthServiceImp {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public UserEntity addUser(SignUpRequest request) {
        UserEntity user = new UserEntity();
        try {
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            userRepository.save(user);
        } catch (Exception error) {
            System.out.println("signup user service error " + error);
        }
        return user;
    }
}
