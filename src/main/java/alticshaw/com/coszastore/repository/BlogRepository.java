package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
}
