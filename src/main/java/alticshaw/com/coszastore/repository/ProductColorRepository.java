package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.ProductColorEntity;
import alticshaw.com.coszastore.entity.ProductTagEntity;
import alticshaw.com.coszastore.entity.ids.ProductColorIds;
import alticshaw.com.coszastore.entity.ids.ProductTagIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductColorRepository extends JpaRepository<ProductColorEntity, ProductColorIds> { }
