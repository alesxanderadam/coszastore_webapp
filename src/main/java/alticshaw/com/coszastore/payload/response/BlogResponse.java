package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.dto.UserDto;
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
    private UserDto user;
    private List<TagResponse> tagList;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int commentQuantity;
}