package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.dto.TokenDto;
import alticshaw.com.coszastore.entity.RoleEntity;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.exception.*;
import alticshaw.com.coszastore.payload.request.SignInRequest;
import alticshaw.com.coszastore.payload.request.SignUpRequest;
import alticshaw.com.coszastore.payload.response.UserResponse;
import alticshaw.com.coszastore.repository.RoleRepository;
import alticshaw.com.coszastore.repository.UserRepository;
import alticshaw.com.coszastore.service.imp.AuthServiceImp;
import alticshaw.com.coszastore.utils.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class AuthService implements AuthServiceImp {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    @Lazy
    JwtService jwtService;

    @Override
    public TokenDto signIn(SignInRequest signInRequest, BindingResult signInBindingResult) {
        validateRequest(signInBindingResult);
        UsernamePasswordAuthenticationToken jwtToken = new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(jwtToken);
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authentication);
            }
        } catch (Exception e) {
            throw new AuthCustomException("Invalid Email or Password! Try again", 401);
        }
        return null;
    }

    @Override
    public UserEntity addUser(SignUpRequest request, BindingResult signUpBindingResult) {
        validateRequest(signUpBindingResult);
        validateUniqueEmail(request.getEmail());
        RoleEntity role = getRoleById(request.getRole_id());
        return saveUser(request, role);
    }

    @Override
    public UserResponse getInfoUser(String token) {
        Claims claims = jwtService.decodeToken(token);
        if (claims != null) {
            String email = claims.get("email", String.class);
            UserEntity user = userRepository.findByEmail(email);
            if (user != null) {
                return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole().getName());
            }
        }
        throw new AuthCustomException("Not authorized", 401);
    }

    private void validateRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationCustomException(bindingResult);
        }
    }

    private void validateUniqueEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictCustomException("Email is already exist!", HttpStatus.CONFLICT.value());
        }
    }

    private RoleEntity getRoleById(int roleId) {
        RoleEntity role = roleRepository.findById(roleId);
        if (role == null) {
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
