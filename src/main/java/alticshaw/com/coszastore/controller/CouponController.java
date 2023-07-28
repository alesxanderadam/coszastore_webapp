package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.request.CouponRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.CouponServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    private final CouponServiceImp couponServiceImp;

    @Autowired
    public CouponController(CouponServiceImp couponServiceImp) {
        this.couponServiceImp = couponServiceImp;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid CouponRequest couponRequest, BindingResult bindingResult) {
        boolean isSuccess = couponServiceImp.add(couponRequest, bindingResult);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Add coupon successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list/all")
    public ResponseEntity<?> getAllCoupons() {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("List all coupons!");
        response.setData(couponServiceImp.getAllCoupons());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable String id,
                                  @RequestBody @Valid CouponRequest couponRequest,
                                  BindingResult bindingResult
    ) {
        boolean isSuccess = couponServiceImp.edit(id, couponRequest, bindingResult);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Edit coupon successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        boolean isSuccess = couponServiceImp.delete(id);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Edit coupon successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
