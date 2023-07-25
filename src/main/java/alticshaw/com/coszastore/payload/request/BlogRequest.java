package alticshaw.com.coszastore.payload.request;

import alticshaw.com.coszastore.exception.NotImageException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Integer user_id;

    @NotNull(message = "Tag list not null!")
    @NotEmpty(message = "Tag list not empty!")
    @Pattern(regexp = "^(?!.*\\b0\\b)(?!.*\\b(\\d+)\\b.*\\b\\1\\b)\\[(?:\\d+(?:,\\d+)*)?]$", message = "Invalid tag list")
    private String tag_id;

    public Set<Integer> getTagIdSet() {
        String tagListStr = this.tag_id;
        return Arrays.stream(tagListStr.replaceAll("[\\[\\]]", "").split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }
}
