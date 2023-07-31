package alticshaw.com.coszastore.entity;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.util.Set;

@Entity(name = "blog")
public class BlogEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "created_at")
    private Timestamp createdTime;

    @Column(name = "updated_at")
    private Timestamp updatedTime;

    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
    private Set<BlogTagEntity> blogTags;

    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
    private Set<CommentEntity> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public Set<BlogTagEntity> getBlogTags() {
        return blogTags;
    }

    public void setBlogTags(Set<BlogTagEntity> blogTags) {
        this.blogTags = blogTags;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }
}
