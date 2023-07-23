package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.response.OrderResponse;

import java.util.List;

public interface OrderServiceImp{
    List<OrderResponse> findAll();

}
