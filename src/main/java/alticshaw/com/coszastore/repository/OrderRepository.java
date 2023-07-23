package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.OrderEntity;
import alticshaw.com.coszastore.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findAll();

}
