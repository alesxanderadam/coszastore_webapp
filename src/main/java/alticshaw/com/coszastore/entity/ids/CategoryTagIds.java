package alticshaw.com.coszastore.entity.ids;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CategoryTagIds implements Serializable {
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "tag_id")
    private int tagId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public CategoryTagIds() {

    }

    public CategoryTagIds(int categoryId, int tagId) {
        this.categoryId = categoryId;
        this.tagId = tagId;
    }
}
