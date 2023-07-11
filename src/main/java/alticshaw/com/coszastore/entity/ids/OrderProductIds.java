package alticshaw.com.coszastore.entity.ids;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderProductIds implements Serializable {
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "product_id")
    private int productId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public OrderProductIds() {

    }

    public OrderProductIds(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
