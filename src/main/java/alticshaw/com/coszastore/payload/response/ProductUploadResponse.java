package alticshaw.com.coszastore.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductUploadResponse {
    private String image;
    private List<String> list_images;
}
