package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
}
