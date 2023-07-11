package alticshaw.com.coszastore.entity;

import alticshaw.com.coszastore.entity.ids.CategoryTagIds;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "category_tag")
public class CategoryTagEntity {
    @EmbeddedId
    private CategoryTagIds ids;

    @ManyToOne
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private TagEntity tag;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    public CategoryTagIds getIds() {
        return ids;
    }

    public void setIds(CategoryTagIds ids) {
        this.ids = ids;
    }

    public TagEntity getTag() {
        return tag;
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
