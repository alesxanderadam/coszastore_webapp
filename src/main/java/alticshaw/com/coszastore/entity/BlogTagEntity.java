package alticshaw.com.coszastore.entity;

import alticshaw.com.coszastore.entity.ids.BlogTagIds;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "blog_tag")
public class BlogTagEntity {
    @EmbeddedId
    private BlogTagIds ids;

    @ManyToOne
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private TagEntity tag;

    @ManyToOne
    @JoinColumn(name = "blog_id", insertable = false, updatable = false)
    private BlogEntity blog;

    public BlogTagIds getIds() {
        return ids;
    }

    public void setIds(BlogTagIds ids) {
        this.ids = ids;
    }

    public TagEntity getTag() {
        return tag;
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
    }

    public BlogEntity getBlog() {
        return blog;
    }

    public void setBlog(BlogEntity blog) {
        this.blog = blog;
    }
}
