package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.*;
import alticshaw.com.coszastore.exception.CustomException;
import alticshaw.com.coszastore.exception.CustomIllegalArgumentException;
import alticshaw.com.coszastore.exception.TagNotFoundException;
import alticshaw.com.coszastore.mapper.ModelUtilMapper;
import alticshaw.com.coszastore.payload.request.OrderRequest;
import alticshaw.com.coszastore.payload.response.OrderResponse;
import alticshaw.com.coszastore.payload.response.ProductResponse;
import alticshaw.com.coszastore.repository.*;
import alticshaw.com.coszastore.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService implements OrderServiceImp {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

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

    @Override
    public boolean addOrder(OrderRequest orderRequest) {
        boolean isSuccess;
        try {
            OrderEntity order = new OrderEntity();
            order.setTotalPrice(orderRequest.getPrice());
            order.setState(orderRequest.getState());
            order.setPostCode(orderRequest.getPostCode());

            CountryEntity country = countryRepository.findById(orderRequest.getCountry_id())
                    .orElseThrow(() -> new TagNotFoundException("Country not found: " + orderRequest.getCountry_id()));
            order.setCountry(country);

            CouponEntity coupon = couponRepository.findById(orderRequest.getCoupon_id())
                    .orElseThrow(() -> new TagNotFoundException("Coupon not found: " + orderRequest.getCoupon_id()));
            order.setCoupon(coupon);

            UserEntity user = userRepository.findById(orderRequest.getUser_id())
                    .orElseThrow(() -> new TagNotFoundException("User not found: " + orderRequest.getUser_id()));
            order.setUser(user);

            orderRepository.save(order);
            isSuccess = true;
        } catch (Exception e) {
            throw new CustomException("Error addOrder " + e.getMessage());

        }
        return isSuccess;
    }



    @Override
    public boolean delete(String id) {
        try {
            int tagId = Integer.parseInt(id);
            OrderEntity order = orderRepository.findById(tagId).orElseThrow(() ->
                    new TagNotFoundException("Order not found with id: " + tagId));
            orderRepository.delete(order);
            return true;
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Illegal Order id: " + id);
        }
    }
}
