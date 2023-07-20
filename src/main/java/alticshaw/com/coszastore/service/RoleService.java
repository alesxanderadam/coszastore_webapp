package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.RoleEntity;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.exception.CustomException;
import alticshaw.com.coszastore.payload.response.UserResponse;
import alticshaw.com.coszastore.repository.RoleRepository;
import alticshaw.com.coszastore.repository.UserRepository;
import alticshaw.com.coszastore.service.imp.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
@Service
public class RoleService implements RoleServiceImp {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleEntity> findAll() {
        List<RoleEntity> roleList = roleRepository.findAll();
        List<RoleEntity> userResponseList = new ArrayList<>();

        for (RoleEntity data : roleList) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(data.getId());
            roleEntity.setName(data.getName());
            roleEntity.setDescription(data.getDescription());
            userResponseList.add(roleEntity);
        }

        return userResponseList;
    }

    @Override
    public RoleEntity addRole(RoleEntity roleEntity) {
        try {
            roleRepository.save(roleEntity);
        } catch (Exception e) {
            throw new CustomException("Error addRole " + e.getMessage());

        }
        return roleEntity;
    }

    @Override
    public RoleEntity updateRole(Integer roleId, RoleEntity updatedRole) {
        RoleEntity existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));
        existingRole.setId(updatedRole.getId());
        existingRole.setName(updatedRole.getName());
        existingRole.setDescription(updatedRole.getDescription());
        roleRepository.save(existingRole);
        return updatedRole;
    }

    @Override
    public boolean deleteRole(Integer roleId, RoleEntity deletedRole) {
        boolean isSuccess;
        RoleEntity deleteRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));
        ;
        roleRepository.delete(deleteRole);
        isSuccess = true;

        return isSuccess;
    }
}
