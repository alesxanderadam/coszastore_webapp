package alticshaw.com.coszastore.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private String content;
    private String email;
    private String website;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int blogId;
}
