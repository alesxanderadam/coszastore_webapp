package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.entity.ColorEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorResponse {
    private int id;
    private String name;

    public ColorResponse mapColorEntityToColorResponse(ColorEntity colorEntity) {
        return new ColorResponse(colorEntity.getId(), colorEntity.getName());
    }
}
