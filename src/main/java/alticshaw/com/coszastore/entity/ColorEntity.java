package alticshaw.com.coszastore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "color")
@Getter
@Setter
public class ColorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "color", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<ProductColorEntity> productColors;
}
