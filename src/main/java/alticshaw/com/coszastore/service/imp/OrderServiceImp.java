package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.entity.OrderEntity;
import alticshaw.com.coszastore.payload.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderServiceImp{
    List<OrderResponse> findAll();

}
