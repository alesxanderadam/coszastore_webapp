package alticshaw.com.coszastore.entity.ids;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class ProductSizeIds implements Serializable {
    @Column(name = "product_id")
    private int productId;

    @Column(name = "size_id")
    private int sizeId;
}
