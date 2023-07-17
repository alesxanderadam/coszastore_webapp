package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.entity.BlogEntity;

import java.sql.Timestamp;

public class CommentResponse {
    private String content;
    private String email;
    private String website;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private BlogEntity blog;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BlogEntity getBlog() {
        return blog;
    }

    public void setBlog(BlogEntity blog) {
        this.blog = blog;
    }

    public CommentResponse() {}

    public CommentResponse(String content, String email, String website,
                           String name, Timestamp createdAt,
                           Timestamp updatedAt, BlogEntity blog) {
        this.content = content;
        this.email = email;
        this.website = website;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.blog = blog;
    }
}
