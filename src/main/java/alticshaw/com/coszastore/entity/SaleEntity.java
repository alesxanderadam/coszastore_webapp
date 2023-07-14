package alticshaw.com.coszastore.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "sale")
public class SaleEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "discount_percentage")
    private float discount_percentage;

    @Column(name = "start_date")
    private Date createdTime;

    @Column(name = "end_date")
    private Date updatedTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
