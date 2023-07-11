package alticshaw.com.coszastore.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "category")
public class CategoryEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<CategoryTagEntity> categoryTags;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<ProductEntity> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CategoryTagEntity> getCategoryTags() {
        return categoryTags;
    }

    public void setCategoryTags(Set<CategoryTagEntity> categoryTags) {
        this.categoryTags = categoryTags;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }
}
