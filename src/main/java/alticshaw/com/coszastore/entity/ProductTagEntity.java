package alticshaw.com.coszastore.entity;

import alticshaw.com.coszastore.entity.ids.ProductSizeIds;
import alticshaw.com.coszastore.entity.ids.ProductTagIds;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "product_tag")
@Getter
@Setter
public class ProductTagEntity {
    @EmbeddedId
    private ProductTagIds ids;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonBackReference
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    @JsonBackReference
    private TagEntity tag;

}
