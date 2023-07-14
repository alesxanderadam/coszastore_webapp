package alticshaw.com.coszastore.entity;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
public class FeedBackEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "star")
    private int star;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
