package alticshaw.com.coszastore.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity(name = "orders")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public Set<OrderProductEntity> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProductEntity> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
