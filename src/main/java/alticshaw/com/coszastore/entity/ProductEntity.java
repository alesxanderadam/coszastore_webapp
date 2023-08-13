package alticshaw.com.coszastore.entity;

import alticshaw.com.coszastore.common.CustomBoolean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity(name = "product")
@Getter
@Setter
public class ProductEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "image")
    private String image;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "import_price")
    private BigDecimal import_price;

    @Column(name = "short_description")
    private String short_description;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "weight")
    private String weight;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "materials")
    private String materials;

    @Column(name = "is_new_product")
//    @JsonSerialize(using = CustomBoolean.class)
    private int isNewProduct;

    @Column(name = "is_best_selling")
//    @JsonSerialize(using = CustomBoolean.class)
        private int isBestSelling;

    @Column(name = "list_image")
    private String listImage;

    @Column(name = "created_at")
    private Timestamp createdTime;

    @Column(name = "updated_at")
    private Timestamp updatedTime;

    @PrePersist
    protected void onCreate() {
        createdTime = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = new Timestamp(new Date().getTime());
    }

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<OrderProductEntity> orderProducts;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductSizeEntity> productSizes;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductColorEntity> productColors;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductTagEntity> productTags;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<SaleEntity> saleEntities;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<FeedBackEntity> feedbacks;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<RateEntity> rateEntities;
}
