package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.RoleEntity;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.response.UserResponse;
import alticshaw.com.coszastore.repository.RoleRepository;
import alticshaw.com.coszastore.repository.UserRepository;
import alticshaw.com.coszastore.service.imp.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoleService implements RoleServiceImp {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleEntity> findAll() {
        List<RoleEntity> roleList = new ArrayList<>(roleRepository.findAll());
        for (RoleEntity data : roleList) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(data.getId());
            roleEntity.setName(data.getName());
            roleEntity.setDescription(data.getDescription());
            roleList.add(roleEntity);
        }

        return roleList;
    }
}
