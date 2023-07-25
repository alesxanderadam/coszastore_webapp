package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.dto.UserResponseWithBlogDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponse {
    private String image;
    private String content;
    private UserResponseWithBlogDto user;
    private List<TagResponse> tags;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int commentQuantity;
}
