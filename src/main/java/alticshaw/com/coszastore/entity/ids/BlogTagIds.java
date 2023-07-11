package alticshaw.com.coszastore.entity.ids;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BlogTagIds implements Serializable {
    @Column(name = "blog_id")
    private int blogId;

    @Column(name = "tag_id")
    private int tagId;

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public BlogTagIds() {

    }
    public BlogTagIds(int blogId, int tagId) {
        this.blogId = blogId;
        this.tagId = tagId;
    }
}
