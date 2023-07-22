package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    Optional<TagEntity> findByName(String tagName);
    TagEntity findById(int id);
}