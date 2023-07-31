package alticshaw.com.coszastore.entity.ids;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
@Data
public class ProductColorIds implements Serializable {
    @Column(name = "product_id")
    private int productId;

    @Column(name = "color_id")
    private int colorId;
}
