package alticshaw.com.coszastore.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "role")
public class RoleEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<UserEntity> userEntities;
}
