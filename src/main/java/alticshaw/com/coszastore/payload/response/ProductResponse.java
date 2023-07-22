package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.entity.ColorEntity;
import alticshaw.com.coszastore.entity.ProductSizeEntity;
import alticshaw.com.coszastore.entity.SizeEntity;
import alticshaw.com.coszastore.entity.TagEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class ProductResponse {
    private int id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String short_description;
    private String description;
    private String weight;
    private String materials;
    private String dimensions;
    private String image;
    private String list_image;
    private int category_id;
    private List<TagResponse> tag;
    private List<String> color;
    private List<String> size;
}
