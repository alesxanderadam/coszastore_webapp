package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.dto.BlogDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponse {
    List<BlogDto> blogs;
    private int currentPage;
    private int totalItems;
    private int totalPages;
}
