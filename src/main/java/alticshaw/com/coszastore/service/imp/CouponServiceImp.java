package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.request.CouponRequest;
import alticshaw.com.coszastore.payload.response.CouponResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CouponServiceImp {
    boolean add(CouponRequest couponRequest, BindingResult bindingResult);
    List<CouponResponse> getAllCoupons();
    boolean edit(String id, CouponRequest couponRequest, BindingResult bindingResult);
    boolean delete(String id);
}
