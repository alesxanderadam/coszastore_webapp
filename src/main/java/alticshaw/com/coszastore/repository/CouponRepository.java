package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Integer> {
    boolean existsByCode(String code);
}
