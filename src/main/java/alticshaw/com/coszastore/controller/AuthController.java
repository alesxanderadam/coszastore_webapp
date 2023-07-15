package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.exception.AuthCustomException;
import alticshaw.com.coszastore.exception.ValidationCustomException;
import alticshaw.com.coszastore.payload.request.SignInRequest;
import alticshaw.com.coszastore.payload.request.SignUpRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.payload.response.UserResponse;
import alticshaw.com.coszastore.repository.UserRepository;
import alticshaw.com.coszastore.service.imp.AuthServiceImp;
import alticshaw.com.coszastore.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // use constructor injection instead of @Autowired -> recommend á
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final BaseResponse baseResponse;
    private final MessageResponse messageResponse;
    private final AuthServiceImp authServiceImp;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtHelper jwtHelper,
            BaseResponse baseResponse,
            MessageResponse messageResponse,
            AuthServiceImp authServiceImp,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.baseResponse = baseResponse;
        this.messageResponse = messageResponse;
        this.authServiceImp = authServiceImp;
        this.userRepository = userRepository;
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest signInRequest, BindingResult signInBindingResult) {
        if (signInBindingResult.hasErrors()) {
            throw new ValidationCustomException(signInBindingResult);
        }
        UsernamePasswordAuthenticationToken jwtToken = new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(jwtToken);
            if (authentication.isAuthenticated()) {
                UserEntity user = userRepository.findByEmail(signInRequest.getEmail());
                UserResponse customUserResp = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole().getName());
                String jwt = jwtHelper.generateToken(customUserResp);
                baseResponse.setStatusCode(200);
                baseResponse.setData(jwt);
                baseResponse.setMessage(new MessageResponse().success());
            }
        } catch (Exception e) {
            throw new AuthCustomException("Invalid Email or Password! Try again", 401);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid SignUpRequest signUpRequest, BindingResult signUpBindingResult) {
        UserEntity user = authServiceImp.addUser(signUpRequest, signUpBindingResult);
        if(user != null){
            UserResponse userResponse = new UserResponse().MapUserEntityToUserResponse(user);
            baseResponse.setStatusCode(200);
            baseResponse.setMessage(messageResponse.success());
            baseResponse.setData(userResponse);
            return ResponseEntity.ok(baseResponse);
        }
        throw new AuthCustomException("Failed to sign up user", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    @GetMapping("/infor")
    public ResponseEntity<?> getInforUser(@RequestParam String token) {
        Claims claims = jwtHelper.decodeToken(token);
        if (claims != null) {
            String email = claims.get("email").toString();
            UserEntity user = userRepository.findByEmail(email);
            baseResponse.setStatusCode(200);
            baseResponse.setMessage(messageResponse.success());
            baseResponse.setData(user);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("Not unthorization to appcet ", HttpStatus.UNAUTHORIZED);
    }
}
