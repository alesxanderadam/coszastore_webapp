package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<SizeEntity, Integer> {
    Optional<SizeEntity> findByName(String name);
}
