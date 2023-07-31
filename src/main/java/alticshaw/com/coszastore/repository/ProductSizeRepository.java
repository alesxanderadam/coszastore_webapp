package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.ProductSizeEntity;
import alticshaw.com.coszastore.entity.ids.ProductSizeIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSizeEntity, ProductSizeIds> {
}
