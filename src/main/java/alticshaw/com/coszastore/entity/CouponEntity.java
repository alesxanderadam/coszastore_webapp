package alticshaw.com.coszastore.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity(name = "coupon")
public class CouponEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "type")
    private String type;

    @Column(name = "promotion")
    private double promotion;

    @Column(name = "created_at")
    private Timestamp createdTime;

    @Column(name = "updated_at")
    private Timestamp updatedTime;

    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    private Set<OrderEntity> orders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPromotion() {
        return promotion;
    }

    public void setPromotion(double promotion) {
        this.promotion = promotion;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }
}
