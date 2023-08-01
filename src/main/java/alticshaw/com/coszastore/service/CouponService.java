package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.CouponEntity;
import alticshaw.com.coszastore.exception.CouponAlreadyExistException;
import alticshaw.com.coszastore.exception.CouponNotFoundException;
import alticshaw.com.coszastore.exception.CustomIllegalArgumentException;
import alticshaw.com.coszastore.exception.CustomValidationException;
import alticshaw.com.coszastore.payload.request.CouponRequest;
import alticshaw.com.coszastore.payload.response.CouponResponse;
import alticshaw.com.coszastore.repository.CouponRepository;
import alticshaw.com.coszastore.service.imp.CouponServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService implements CouponServiceImp {
    private final CouponRepository couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public boolean add(CouponRequest couponRequest, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            if (couponRepository.existsByCode(couponRequest.getCode())) {
                throw new CouponAlreadyExistException("Coupon already existed with this code: " + couponRequest.getCode());
            }
            CouponEntity couponEntity = new CouponEntity();
            couponEntity.setCode(couponRequest.getCode());
            couponEntity.setType(couponRequest.getType());
            couponEntity.setPromotion(couponRequest.getPromotion());
            couponEntity.setCreatedTime(new Timestamp(System.currentTimeMillis()));

            couponRepository.save(couponEntity);
            return true;
        } else {
            throw new CustomValidationException(bindingResult);
        }
    }

    @Override
    public List<CouponResponse> getAllCoupons() {
        List<CouponEntity> couponEntities = couponRepository.findAll();
        return couponEntities.stream()
                .map(data -> new CouponResponse().mapToCouponResponse(data))
                .collect(Collectors.toList());
    }

    @Override
    public boolean edit(String id, CouponRequest couponRequest, BindingResult bindingResult) {
        try {
            int couponId = Integer.parseInt(id);
            if (!bindingResult.hasErrors()) {
                CouponEntity couponEntity = couponRepository.findById(couponId)
                        .orElseThrow(() -> new CouponNotFoundException("Coupon not found with id: " + couponId));
                couponEntity.setCode(couponRequest.getCode());
                couponEntity.setType(couponRequest.getType());
                couponEntity.setPromotion(couponRequest.getPromotion());
                couponEntity.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
                couponRepository.save(couponEntity);
                return true;
            } else {
                throw new CustomValidationException(bindingResult);
            }
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Illegal parameter id: " + id);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            int couponId = Integer.parseInt(id);
            CouponEntity couponEntity = couponRepository.findById(couponId)
                    .orElseThrow(() -> new CouponNotFoundException("Coupon not found with id: " + couponId));
            couponRepository.delete(couponEntity);
            return true;
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Illegal parameter id: " + id);
        }
    }
}
