package alticshaw.com.coszastore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "tag")
@Data
public class TagEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    private Set<BlogTagEntity> blogTags;

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    private Set<CategoryTagEntity> categoryTags;

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<ProductTagEntity> productTags;

}
