package alticshaw.com.coszastore.payload.request;

import javax.validation.constraints.*;

public class CommentRequest {

    @NotNull(message = "Content is required!")
    @NotEmpty(message = "Content is not empty!")
    private String content;

    @NotNull(message = "Email can not be null!")
    @NotEmpty(message = "Email can not be empty!")
    @Email(message = "Email must be in right format!",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;
    private String website;

    @NotNull(message = "Name is required!")
    @NotEmpty(message = "Name is not empty!")
    private String name;

    @NotNull(message = "Blog is not null!")
    @NotEmpty(message = "Blog is required!")
    @Pattern(regexp = "^[1-9]\\d*$")
    private int blogId;

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

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public CommentRequest(String content, String email, String website, String name, int blogId) {
        this.content = content;
        this.email = email;
        this.website = website;
        this.name = name;
        this.blogId = blogId;
    }
}
