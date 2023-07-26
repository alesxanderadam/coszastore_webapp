package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.OrderEntity;
import alticshaw.com.coszastore.entity.OrderProductEntity;
import alticshaw.com.coszastore.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findAll();


}
