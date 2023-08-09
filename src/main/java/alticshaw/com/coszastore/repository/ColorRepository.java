package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<ColorEntity, Integer> {
    Optional<ColorEntity> findByName(String name);
}
