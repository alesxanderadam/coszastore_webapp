package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.dto.SignInDto;
import alticshaw.com.coszastore.dto.TokenDto;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.request.SignInRequest;
import alticshaw.com.coszastore.payload.request.SignUpRequest;
import alticshaw.com.coszastore.payload.response.UserResponse;
import org.springframework.validation.BindingResult;

public interface AuthServiceImp {
    TokenDto signIn(SignInRequest signInRequest, BindingResult signInBindingResult);
//    TokenDto signIn(SignInDto signInDto, BindingResult signInBindingResult);
    UserEntity addUser(SignUpRequest request, BindingResult signUpBindingResult);

    UserResponse getInfoUser(String token);
}
