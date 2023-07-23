package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
    List<ProductEntity> findByName(String name);
    @Query("SELECT DISTINCT p FROM product p JOIN FETCH p.productSizes ps JOIN FETCH ps.size JOIN FETCH p.productColors pc JOIN FETCH pc.color")
    Set<ProductEntity> findAllProductsWithSizes();
}
