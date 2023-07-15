package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.RoleEntity;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.exception.*;
import alticshaw.com.coszastore.payload.request.SignUpRequest;
import alticshaw.com.coszastore.repository.RoleRepository;
import alticshaw.com.coszastore.repository.UserRepository;
import alticshaw.com.coszastore.service.imp.AuthServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AuthService implements AuthServiceImp {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity addUser(SignUpRequest request, BindingResult signUpBindingResult) {
        validateSignUpRequest(signUpBindingResult);
        validateUniqueEmail(request.getEmail());
        RoleEntity role = getRoleById(request.getRole_id());
        return saveUser(request, role);
    }

    private void validateSignUpRequest(BindingResult signUpBindingResult) {
        if (signUpBindingResult.hasErrors()) {
            throw new ValidationCustomException(signUpBindingResult);
        }
    }

    private void validateUniqueEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new ConflictCustomException("Email is already exist!", HttpStatus.CONFLICT.value());
        }
    }

    private RoleEntity getRoleById(int roleId) {
        RoleEntity role = roleRepository.findById(roleId);
        if(role == null){
            throw new RoleCustomException("Role id not exist!", 404);
        }
        return role;
    }

    private UserEntity saveUser(SignUpRequest request, RoleEntity role) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(role);
        return userRepository.save(user);
    }
}
