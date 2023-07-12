package alticshaw.com.coszastore.services.imp;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.request.SignUpRequest;

public interface AuthServiceImp {
    UserEntity addUser(SignUpRequest request);
}
