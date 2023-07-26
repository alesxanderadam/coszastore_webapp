package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.entity.OrderEntity;
import alticshaw.com.coszastore.payload.request.OrderRequest;
import alticshaw.com.coszastore.payload.response.OrderResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface OrderServiceImp{
    List<OrderResponse> findAll();
//    OrderResponse getOrderById(Integer id);
    boolean addOrder(OrderRequest orderRequest);
    boolean delete(String id);
}
