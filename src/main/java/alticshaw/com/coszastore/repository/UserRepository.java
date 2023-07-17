package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    List<UserEntity> findAll();
}
