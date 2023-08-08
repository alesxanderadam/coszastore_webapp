package alticshaw.com.coszastore.entity;

import alticshaw.com.coszastore.entity.ids.ProductColorIds;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "product_color")
@Getter
@Setter
public class ProductColorEntity {
    @EmbeddedId
    private ProductColorIds ids;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "color_id", insertable = false, updatable = false)
    @JsonBackReference
    private ColorEntity color;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonBackReference
    private ProductEntity product;
}
