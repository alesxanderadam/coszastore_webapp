package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.dto.TokenDto;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.exception.AuthCustomException;
import alticshaw.com.coszastore.payload.request.SignInRequest;
import alticshaw.com.coszastore.payload.request.SignUpRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.payload.response.UserResponse;
import alticshaw.com.coszastore.service.imp.AuthServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // use constructor injection instead of @Autowired -> recommend á
    private final BaseResponse baseResponse;
    private final MessageResponse messageResponse;
    private final AuthServiceImp authServiceImp;

    @Autowired
    public AuthController(
            BaseResponse baseResponse,
            MessageResponse messageResponse,
            AuthServiceImp authServiceImp) {
        this.baseResponse = baseResponse;
        this.messageResponse = messageResponse;
        this.authServiceImp = authServiceImp;
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest signInRequest, BindingResult signInBindingResult) {
        TokenDto jwt = authServiceImp.signIn(signInRequest, signInBindingResult);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(messageResponse.success());
        baseResponse.setData(jwt);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult signUpBindingResult) {
        UserEntity user = authServiceImp.addUser(signUpRequest, signUpBindingResult);
        if (user != null) {
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
        UserResponse userResponse = authServiceImp.getInfoUser(token);
        if (userResponse != null) {
            baseResponse.setStatusCode(200);
            baseResponse.setMessage(messageResponse.success());
            baseResponse.setData(userResponse);
            return ResponseEntity.ok(baseResponse);
        }
        throw new AuthCustomException("Failed to get information user", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
