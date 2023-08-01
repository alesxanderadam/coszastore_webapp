package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.entity.CouponEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponResponse {
    private int id;
    private String code;
    private String type;
    private double promotion;
    private Timestamp created_at;
    private Timestamp updated_at;

    public CouponResponse mapToCouponResponse(CouponEntity couponEntity) {
        return new CouponResponse(
                couponEntity.getId(),
                couponEntity.getCode(),
                couponEntity.getType(),
                couponEntity.getPromotion(),
                couponEntity.getCreatedTime(),
                couponEntity.getUpdatedTime()
        );
    }
}
