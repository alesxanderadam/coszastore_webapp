package alticshaw.com.coszastore.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "tag")
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

    public Set<BlogTagEntity> getBlogTags() {
        return blogTags;
    }

    public void setBlogTags(Set<BlogTagEntity> blogTags) {
        this.blogTags = blogTags;
    }

    public Set<CategoryTagEntity> getCategoryTags() {
        return categoryTags;
    }

    public void setCategoryTags(Set<CategoryTagEntity> categoryTags) {
        this.categoryTags = categoryTags;
    }
}
