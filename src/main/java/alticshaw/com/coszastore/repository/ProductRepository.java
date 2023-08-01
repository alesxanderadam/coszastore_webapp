package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
    @Query("SELECT DISTINCT p FROM product p JOIN FETCH p.category c JOIN FETCH p.productTags JOIN FETCH  p.productSizes JOIN FETCH p.productColors")
    List<ProductEntity> findAllProductsCustom();

    @Query("SELECT p FROM product p JOIN FETCH p.category c JOIN FETCH p.productTags JOIN FETCH  p.productSizes JOIN FETCH p.productColors WHERE p.id = :id")
    ProductEntity findByProductCustom(int id);
}
