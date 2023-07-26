package alticshaw.com.coszastore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity(name = "orders")
@Getter
@Setter
public class OrderEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "state")
    private String state;

    @Column(name = "postcode")
    private String postCode;

    @Column(name = "created_at")
    private Timestamp createdTime;

    @Column(name = "updated_at")
    private Timestamp updatedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderProductEntity> orderProducts;

    @PrePersist
    protected void onCreate() {
        createdTime = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = new Timestamp(new Date().getTime());
    }
}
