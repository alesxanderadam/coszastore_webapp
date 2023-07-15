package alticshaw.com.coszastore.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity(name = "product")
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
    private double price;

    @Column(name = "short_description")
    private String short_description;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "weight")
    private String weight;

    @Column(name = "dimensions ")
    private String dimensions;

    @Column(name = "materials")
    private String materials;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

    @Column(name = "list_image")
    private String listImage;

    @Column(name = "created_at")
    private Timestamp createdTime;

    @Column(name = "updated_at")
    private Timestamp updatedTime;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<OrderProductEntity> orderProducts;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private ColorEntity color;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private SizeEntity size;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<SaleEntity> saleEntities;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<FeedBackEntity> feedbacks;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<RateEntity> rateEntities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getListImage() {
        return listImage;
    }

    public void setListImage(String listImage) {
        this.listImage = listImage;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<OrderProductEntity> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProductEntity> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public ColorEntity getColor() {
        return color;
    }

    public void setColor(ColorEntity color) {
        this.color = color;
    }

    public SizeEntity getSize() {
        return size;
    }

    public void setSize(SizeEntity size) {
        this.size = size;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public TagEntity getTag() {
        return tag;
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
    }

    public Set<SaleEntity> getSaleEntities() {
        return saleEntities;
    }

    public void setSaleEntities(Set<SaleEntity> saleEntities) {
        this.saleEntities = saleEntities;
    }

    public Set<FeedBackEntity> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<FeedBackEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<RateEntity> getRateEntities() {
        return rateEntities;
    }

    public void setRateEntities(Set<RateEntity> rateEntities) {
        this.rateEntities = rateEntities;
    }
}
