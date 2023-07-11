package alticshaw.com.coszastore.entity;

import alticshaw.com.coszastore.entity.ids.OrderProductIds;

import javax.persistence.*;

@Entity(name = "orders_product")
public class OrderProductEntity {
    @EmbeddedId
    private OrderProductIds ids;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductEntity product;

    public OrderProductIds getIds() {
        return ids;
    }

    public void setIds(OrderProductIds ids) {
        this.ids = ids;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
