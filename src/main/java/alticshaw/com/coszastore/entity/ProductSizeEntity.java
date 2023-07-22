package alticshaw.com.coszastore.entity;

import alticshaw.com.coszastore.entity.ids.ProductSizeIds;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "product_size")
@Getter
@Setter
public class ProductSizeEntity {
    @EmbeddedId
    private ProductSizeIds ids;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "size_id", insertable = false, updatable = false)
    @JsonBackReference
    private SizeEntity size;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonBackReference
    private ProductEntity product;
}
