package alticshaw.com.coszastore.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequest {
    @NotNull(message = "Title can not null!")
    @NotEmpty(message = "Title can not empty!")
    private String title;

    @NotNull(message = "Content can not null!")
    @NotEmpty(message = "Content can not empty!")
    private String content;
    private MultipartFile image;

    @NotNull(message = "User id can not null!")
    @Positive(message = "Invalid user id")
    private int userId;

    @NotNull(message = "Tag list not null!")
    @NotEmpty(message = "Tag list not empty!")
    @Pattern(regexp = "^(?!.*\\b0\\b)(?!.*\\b(\\d+)\\b.*\\b\\1\\b)\\[(?:\\d+(?:,\\d+)*)?]$", message = "Invalid tag list")
    private String tagList;

    public int[] getTagIdArray() {
        String tagListStr = this.tagList;
        return Arrays.stream(tagListStr.replaceAll("[\\[\\]]", "").split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
