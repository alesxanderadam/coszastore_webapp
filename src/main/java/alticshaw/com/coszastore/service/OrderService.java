package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.OrderEntity;
import alticshaw.com.coszastore.payload.response.OrderResponse;
import alticshaw.com.coszastore.repository.OrderRepository;
import alticshaw.com.coszastore.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements OrderServiceImp {
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public List<OrderResponse> findAll() {
        List<OrderEntity> orderList = orderRepository.findAll();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (OrderEntity data : orderList) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(data.getId());
            orderResponse.setTotalPrice(data.getTotalPrice());
            orderResponse.setState(data.getState());
            orderResponse.setPostCode(data.getPostCode());
            orderResponse.setCountryName(data.getCountry().getName());
            orderResponse.setCouponCode(data.getCoupon().getCode());
            orderResponse.setUserName(data.getUser().getUsername());
            orderResponseList.add(orderResponse);
        }
        return null;
    }
}
