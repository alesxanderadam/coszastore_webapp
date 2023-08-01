package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findById(int role_id);
    java.util.Optional<RoleEntity> findRoleById(int role_id);
}
