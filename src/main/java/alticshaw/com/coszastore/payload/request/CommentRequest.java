package alticshaw.com.coszastore.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Digits(integer = 10, fraction = 0, message = "Blog id must be an integer!")
    private int blogId;
}
