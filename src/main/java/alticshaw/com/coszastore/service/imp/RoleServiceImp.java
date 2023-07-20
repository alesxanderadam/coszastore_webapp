package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.entity.RoleEntity;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.response.UserResponse;

import java.util.List;

public interface RoleServiceImp {
    List<RoleEntity> findAll();

    RoleEntity addRole(RoleEntity roleEntity);

    RoleEntity updateRole(Integer userId,RoleEntity updatedRole);

    boolean deleteRole(Integer userId,RoleEntity updatedRole);

}
