package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.OrderEntity;
import alticshaw.com.coszastore.entity.OrderProductEntity;
import alticshaw.com.coszastore.entity.ProductEntity;
import alticshaw.com.coszastore.mapper.ModelUtilMapper;
import alticshaw.com.coszastore.payload.response.OrderResponse;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.repository.OrderRepository;
import alticshaw.com.coszastore.repository.ProductRepository;
import alticshaw.com.coszastore.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService implements OrderServiceImp {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderResponse> findAll() {
        List<OrderEntity> orderList = orderRepository.findAll();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (OrderEntity data : orderList) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(data.getId());
            orderResponse.setTotal_price(data.getTotalPrice());
            orderResponse.setState(data.getState());
            orderResponse.setPostcode(data.getPostCode());
            orderResponse.setCountry_name(data.getCountry().getName());
            orderResponse.setCoupon_code(data.getCoupon().getCode());
            orderResponse.setUser_name(data.getUser().getUsername());
            Set<ProductResponse> productResponses = new HashSet<>();
            for (OrderProductEntity orderProductEntity : data.getOrderProducts()) {
               ProductEntity productEntity = orderProductEntity.getProduct();
               ProductResponse productResponse = new ModelUtilMapper().map(productEntity, ProductResponse.class);
               productResponses.add(productResponse);
            }
            orderResponse.setProduct(productResponses);
            orderResponseList.add(orderResponse);
        }
        return orderResponseList;
    }
}
