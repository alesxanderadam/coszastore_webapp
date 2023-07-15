package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.request.SignUpRequest;
import org.springframework.validation.BindingResult;

public interface AuthServiceImp {
    UserEntity addUser(SignUpRequest request, BindingResult signUpBindingResult);
}
