package alticshaw.com.coszastore.services.imp;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.request.AuthRequest;

public interface AuthServiceImp {
    UserEntity addUser(AuthRequest request);
}
