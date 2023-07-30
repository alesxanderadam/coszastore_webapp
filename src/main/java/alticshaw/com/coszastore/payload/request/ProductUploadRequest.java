package alticshaw.com.coszastore.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
public class ProductUploadRequest {
    @NotNull(message = "Image cannot be null")
    private MultipartFile image;

    @NotNull(message = "ListImage cannot be null")
    private List<MultipartFile> listImage;

}
